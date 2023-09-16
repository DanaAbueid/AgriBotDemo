package com.icare.icare.backend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.icare.icare.models.SummaryData

interface SummaryApi {
    @GET("api/summary/{user_id}/healthyCrop/today")
    fun getAverageTodayCropHealthy(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/healthyCrop/thisMonth")
    fun getMonthCropHealthy(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/healthyCrop/thisYear")
    fun getAverageYearCropHealthy(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/lightCrop/today")
    fun getAverageTodayLightCrop(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/lightCrop/thisMonth")
    fun getAverageThisMonthLightCrop(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/lightCrop/thisYear")
    fun getAverageThisYearLightCrop(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/cropEarlyBlight/today")
    fun getAverageTodayCropEarlyBlight(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/cropEarlyBlight/thisMonth")
    fun getAverageThisMonthCropEarlyBlight(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/cropEarlyBlight/thisYear")
    fun getAverageThisYearCropEarlyBlight(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/humidity/today")
    fun getAverageTodayHumidity(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/humidity/thisMonth")
    fun getAverageThisMonthHumidity(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/humidity/thisYear")
    fun getAverageThisYearHumidity(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/temperature/today")
    fun getAverageTodayTemperature(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/temperature/thisMonth")
    fun getAverageThisMonthTemperature(@Path("user_id") userId: Long): Call<Double>

    @GET("api/summary/{user_id}/temperature/thisYear")
    fun getAverageThisYearTemperature(@Path("user_id") userId: Long): Call<Double>
}
