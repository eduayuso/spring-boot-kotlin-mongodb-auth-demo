package com.eduayuso.authmongodbdemo.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Document(collection = "users")
class User (

    @Id
    var id: ObjectId = ObjectId.get(),

    @NotBlank
    @Size(max = 20)
    var username: String,

    @NotBlank
    @Size(max = 50)
    @Email
    var email: String,

    @NotBlank
    @Size(max = 120)
    var password: String,
) {

    @DBRef
    var roles: Set<Role>? = null
}