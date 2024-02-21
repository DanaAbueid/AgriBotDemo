package com.icare.icare.models

data class AuthenticationResponse(
    val accessToken: String = "",
    val refreshToken: String = "",
    val user_id: Long
)