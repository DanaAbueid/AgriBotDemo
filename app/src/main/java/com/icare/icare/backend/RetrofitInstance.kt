package com.icare.icare.backend
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://localhost:8080"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createProgressApi(): ProgressApi {
        return retrofit.create(ProgressApi::class.java)
    }
    fun createUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    fun ReportApi(): ReportApi {
        return retrofit.create(ReportApi::class.java)
    }
    fun SubscriptionPlanApi(): SubscriptionPlanApi {
        return retrofit.create(SubscriptionPlanApi::class.java)
    }
    fun SummaryApi(): SummaryApi {
        return retrofit.create(SummaryApi::class.java)
    }
    fun UserSubscriptionApi(): UserSubscriptionApi {
        return retrofit.create(UserSubscriptionApi::class.java)
    }



}
