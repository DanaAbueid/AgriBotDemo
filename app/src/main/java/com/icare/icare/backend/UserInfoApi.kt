package com.icare.icare.backend
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInfoApi {
    @GET("/api/user/{user_id}/username")
     fun getUserId(@Path("user_id") userId: Long): Call<String>

    @GET("/api/user/{user_id}/firstName")
     fun getUserFirstname(@Path("user_id") userId: Long): Call<String>

    @GET("/api/user/{user_id}/lastName")
     fun getUserLastName(@Path("user_id") userId: Long): Call<String>

    @GET("/api/user/{user_id}/location")
     fun getUserLocation(@Path("user_id") userId: Long): Call<String>

    @GET("/api/user/{user_id}/phoneNumber")
     fun getPhoneNumber(@Path("user_id") userId: Long): Call<String>

    @GET("/api/user/{user_id}/status")
    fun getAccountStatus(@Path("user_id") userId: Long): Call<Boolean>
}
