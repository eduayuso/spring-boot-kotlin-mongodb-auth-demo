package com.eduayuso.authmongodbdemo.repository

import com.eduayuso.authmongodbdemo.model.ERole
import com.eduayuso.authmongodbdemo.model.Role
import org.springframework.data.mongodb.repository.MongoRepository

interface RoleRepository : MongoRepository<Role, String> {

    fun findByName(name: ERole): Role?
}