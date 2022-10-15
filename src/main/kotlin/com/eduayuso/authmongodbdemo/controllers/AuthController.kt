package com.eduayuso.authmongodbdemo.controllers

import com.eduayuso.authmongodbdemo.model.ERole
import com.eduayuso.authmongodbdemo.model.Role
import com.eduayuso.authmongodbdemo.model.RoleBuilder
import com.eduayuso.authmongodbdemo.model.User
import com.eduayuso.authmongodbdemo.payload.request.LoginRequest
import com.eduayuso.authmongodbdemo.payload.request.SignupRequest
import com.eduayuso.authmongodbdemo.payload.response.JwtResponse
import com.eduayuso.authmongodbdemo.payload.response.MessageResponse
import com.eduayuso.authmongodbdemo.repository.RoleRepository
import com.eduayuso.authmongodbdemo.repository.UserRepository
import com.eduayuso.authmongodbdemo.security.jwt.JwtUtils
import com.eduayuso.authmongodbdemo.security.services.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtUtils: JwtUtils? = null

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {

        val authentication: Authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils!!.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles: List<String> = userDetails.authorities.stream()
            .map { item: GrantedAuthority -> item.authority }
            .collect(Collectors.toList())
        return ResponseEntity.ok<Any>(
            JwtResponse(
                jwt,
                userDetails.id,
                userDetails.username,
                userDetails.getEmail(),
                roles
            )
        )
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest): ResponseEntity<*> {

        if (userRepository!!.existsByUsername(signUpRequest.username)) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository!!.existsByEmail(signUpRequest.email)) {
            return ResponseEntity
                .badRequest()
                .body<Any>(MessageResponse("Error: Email is already in use!"))
        }

        val user = User(
            username = signUpRequest.username,
            email = signUpRequest.email,
            password = encoder!!.encode(signUpRequest.password)
        )
        val strRoles = signUpRequest.roles
        val roles: MutableSet<Role> = HashSet()
        if (strRoles == null) {
            roleRepository?.findByName(ERole.ROLE_USER)?.let {
                roles.add(it)
            } ?: run {
                RuntimeException("Error: Role is not found.")
            }

        } else {
            strRoles.forEach { roleStr ->
                val role = RoleBuilder.from(roleStr)
                roleRepository?.findByName(role)?.let {
                    roles.add(it)
                } ?: run {
                    RuntimeException("Error: Role is not found.")
                }
            }
        }
        user.roles = roles
        userRepository!!.save<User>(user)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }
}