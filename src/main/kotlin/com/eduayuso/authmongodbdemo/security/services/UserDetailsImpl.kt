package com.eduayuso.authmongodbdemo.security.services

import com.eduayuso.authmongodbdemo.model.User
import org.bson.types.ObjectId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetailsImpl(

    val id: ObjectId,
    private val username: String,
    private val email: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>

): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    fun getEmail(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as UserDetailsImpl
        return Objects.equals(id, user.id)
    }

    object Builder {

        fun build(user: User): UserDetailsImpl {

            val authorities = user.roles?.map {
                    role -> SimpleGrantedAuthority(role.name.name)
            }?.toList()

            return UserDetailsImpl(
                id = user.id,
                username = user.username,
                email = user.email,
                password = user.password,
                authorities = authorities ?: emptyList()
            )
        }
    }
}