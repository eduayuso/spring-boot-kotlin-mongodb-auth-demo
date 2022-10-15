package com.eduayuso.authmongodbdemo.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignupRequest(

    @NotBlank
    @Size(min = 3, max = 20)
    var username: String,

    @NotBlank
    @Size(max = 50)
    @Email
    var email: String,

    @NotBlank
    @Size(min = 6, max = 40)
    var password: String
) {

    var roles: Set<String>? = null
}