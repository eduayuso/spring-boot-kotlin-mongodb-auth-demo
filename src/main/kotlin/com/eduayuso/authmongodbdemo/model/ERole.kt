package com.eduayuso.authmongodbdemo.model

enum class ERole {

    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN
}

object RoleBuilder {

    fun from(string: String): ERole {

        return when(string) {
            "ROLE_ADMIN" -> ERole.ROLE_ADMIN
            "ROLE_MODERATOR" -> ERole.ROLE_MODERATOR
            "ROLE_USER" -> ERole.ROLE_USER
            else -> ERole.ROLE_USER
        }
    }
}