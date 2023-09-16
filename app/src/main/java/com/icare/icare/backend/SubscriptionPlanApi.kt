package com.icare.icare.backend
import retrofit2.Call
import retrofit2.http.GET
import com.icare.icare.models.SubscriptionPlanDBO

interface SubscriptionPlanApi {
    @GET("api/subPlan")
    fun getAllSubPlans(): Call<List<SubscriptionPlanDBO>>
}
