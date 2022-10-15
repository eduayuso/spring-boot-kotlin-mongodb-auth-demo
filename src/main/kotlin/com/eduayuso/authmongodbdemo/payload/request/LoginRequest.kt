package com.eduayuso.authmongodbdemo.payload.request

import javax.validation.constraints.NotBlank

class LoginRequest(

    @NotBlank
    var username: String,

    @NotBlank
    var password: String,
)