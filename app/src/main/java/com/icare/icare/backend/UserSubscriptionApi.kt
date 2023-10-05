package com.icare.icare.backend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.security.Timestamp

// UserSubscriptionApi.kt
interface UserSubscriptionApi {
    @POST("/api/v1/userSubPlan/{user_id}/{subscription_plan_id}")
    fun addSubscriptionPlan(
        @Path("user_id") userId: Long,
        @Path("subscription_plan_id") subscriptionPlanId: Long
    ): Call<Void>

    @GET("/api/v1/userSubPlan/{user_id}/SubID")
    fun getSubscriptionId(@Path("user_id") userId: Long): Call<Long>

    @GET("/api/v1/userSubPlan/{user_id}/SubEndDate")
    fun getSubscriptionEndDate(@Path("user_id") userId: Long): Call<Timestamp>

    @GET("/api/v1/userSubPlan/{user_id}/SubStartDate")
    fun getSubscriptionStartDate(@Path("user_id") userId: Long): Call<Timestamp>

    @GET("/api/v1/userSubPlan/{user_id}/SubPrice")
    fun getSubscriptionPrice(@Path("user_id") userId: Long): Call<Double>
}
