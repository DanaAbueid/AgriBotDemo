package com.icare.icare.Respones

data class AuthenticationResponse(
    val accessToken: String = "",
    val refreshToken: String = ""
)