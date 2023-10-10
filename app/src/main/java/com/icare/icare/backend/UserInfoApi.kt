package com.icare.icare.backend
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInfoApi {
    @GET("/api/user/{user_id}/username")
    suspend fun getUserId(@Path("user_id") userId: Long?): Long

    @GET("/api/user/{user_id}/firstName")
    suspend fun getUserFirstname(@Path("user_id") userId: Long?): String

    @GET("/api/user/{user_id}/lastName")
    suspend fun getUserLastName(@Path("user_id") userId: Long?): String

    @GET("/api/user/{user_id}/location")
    suspend fun getUserLocation(@Path("user_id") userId: Long?): String

    @GET("/api/user/{user_id}/phoneNumber")
    suspend fun getPhoneNumber(@Path("user_id") userId: Long?): String

    @GET("/api/user/{user_id}/status")
    suspend fun getAccountStatus(@Path("user_id") userId: Long?): Boolean
}
