package com.icare.icare.Respones
import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("accessToken")
    var accessToken: String,
    @SerializedName("refreshToken")
    var refreshToken: String,
    @SerializedName("code")
        var code: Long
)