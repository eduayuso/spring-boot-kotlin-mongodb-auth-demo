package com.eduayuso.authmongodbdemo.payload.response

import org.bson.types.ObjectId

class JwtResponse(
    var accessToken: String,
    var id: ObjectId,
    var username: String,
    var email: String,
    val roles: List<String>
) {

    var tokenType = "Bearer"
}