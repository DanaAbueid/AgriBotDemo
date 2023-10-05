package com.icare.icare.backend
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8081"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()



    fun UserSubscriptionApi(): UserSubscriptionApi {
        return retrofit.create(UserSubscriptionApi::class.java)
    }
    fun createProgressApi(): ProgressApi {
        return retrofit.create(ProgressApi::class.java)
    }

    fun createUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    fun createAuthService(): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    fun ReportApi(): ReportApi {
        return retrofit.create(ReportApi::class.java)
    }

    fun SubscriptionPlanApi(): SubscriptionPlanApi {
        return retrofit.create(SubscriptionPlanApi::class.java)
    }

    fun createSummaryApi(): SummaryApi {
        return retrofit.create(SummaryApi::class.java)
    }



    fun UserInfoApi(): UserInfoApi {
        return retrofit.create(UserInfoApi::class.java)
    }
    fun createReportApi(): ReportApiService {
        return retrofit.create(ReportApiService::class.java)
    }
}

