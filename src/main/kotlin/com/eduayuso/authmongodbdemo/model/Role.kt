package com.eduayuso.authmongodbdemo.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "roles")
class Role(

    @Id
    var id: ObjectId = ObjectId.get(),
    var name: ERole
)