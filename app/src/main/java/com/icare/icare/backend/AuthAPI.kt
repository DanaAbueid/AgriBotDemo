package com.icare.icare.backend

import com.icare.icare.models.AuthenticationResponse
import com.icare.icare.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI{
       @POST("/api/v1/auth/register")
       suspend fun register(@Body request: RegisterRequest) : Response<AuthenticationResponse>
}