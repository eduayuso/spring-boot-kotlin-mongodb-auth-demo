package com.eduayuso.authmongodbdemo.security.services

import com.eduayuso.authmongodbdemo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    var userRepository: UserRepository? = null

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        userRepository?.findByUsername(username)?.let {
            return UserDetailsImpl.Builder.build(it)
        } ?: run {
            throw UsernameNotFoundException("User Not Found with username: $username")
        }
    }
}