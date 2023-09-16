package com.icare.icare.backend
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("api/user/{user_id}/status")
    fun getAccountStatus(@Path("user_id") userId: Long): Call<Boolean>
}
