package com.icare.icare.backend
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val BASE_URL = "http://18.234.66.152:8080"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun createAuthService(): AuthService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthService::class.java)
    }

    fun createprogService(): ProgressApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ProgressApi::class.java)
    }

    fun createinfoService(): UserInfoApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UserInfoApi::class.java)
    }




    fun UserSubscriptionApi(): UserSubscriptionApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(UserSubscriptionApi::class.java)
    }
    fun createProgressApi(): ProgressApi {
        return retrofit.create(ProgressApi::class.java)
    }

    fun createUserApi(): UserApi {
        return retrofit.create(UserApi::class.java)
    }


    fun CreateReportApi(): ReportApi {
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

