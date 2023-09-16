package com.icare.icare.backend

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface UserSubscriptionApi {
    @POST("api/userSubPlan/{user_id}/{subscription_plan_id}")
    fun addSubscriptionPlan(
        @Path("user_id") userId: Long,
        @Path("subscription_plan_id") subscriptionPlanId: Long
    ): Call<Void>
}
