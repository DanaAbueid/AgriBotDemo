package com.icare.icare.backend

import com.icare.icare.Request.AuthenticationRequest
import com.icare.icare.models.AuthenticationResponse
import com.icare.icare.models.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST("/register")
    fun register(@Body request: RegisterRequest): Call<AuthenticationResponse>
    @POST("/authenticate")
    fun login(@Body request: AuthenticationRequest): Call<AuthenticationResponse>
}