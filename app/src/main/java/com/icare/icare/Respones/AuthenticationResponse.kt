package com.icare.icare.Respones
import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("user_id")
        val userId: Long?
)