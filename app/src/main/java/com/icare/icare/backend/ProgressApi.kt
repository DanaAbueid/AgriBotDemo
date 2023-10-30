package com.icare.icare.backend

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.PUT
import retrofit2.http.Query
import java.sql.Timestamp

interface ProgressApi {

    @PUT("api/progress/{progress_id}/endTime")
    fun endTime(
        @Path("progress_id") progressId: Long,
        @Query("current") current: Timestamp
    ): Call<String>


    @GET("api/progress/{user_id}/progressIdCurrentTime")
    fun getProgressIdCurrentTime(@Path("user_id") userId: Long): Call<Long>

    @GET("api/progress/{progress_id}/battery")
    fun getBatteryLevel(@Path("progress_id") progressId: Long): Call<Int>

    @GET("api/progress/{progress_id}/soilTemperature")
    fun getSoilTemperature(@Path("progress_id") progressId: Long): Call<Double>

    @GET("api/progress/{progress_id}/soilHumidity")
    fun getSoilHumidity(@Path("progress_id") progressId: Long): Call<Double>

    @GET("api/progress/{progress_id}/soilHealth")
    fun getSoilHealth(@Path("progress_id") progressId: Long): Call<Boolean>

    @GET("api/progress/{progress_id}/remainingChemicals")
    fun getRemainingChemicals(@Path("progress_id") progressId: Long): Call<Double>

    @PUT("api/progress/{progress_id}/weedCounter")
    fun weedCounter(@Path("progress_id") progressId: Long): Call<Void>

    @PUT("api/progress/{progress_id}/noWeedCounter")
    fun noWeedCounter(@Path("progress_id") progressId: Long): Call<Void>

    @GET("api/progress/{progress_id}/weedCounter")
    fun getWeedCounter(@Path("progress_id") progressId: Long): Call<Int>

    @GET("api/progress/{progress_id}/noWeedCounter")
    fun getNoWeedCounter(@Path("progress_id") progressId: Long): Call<Int>

    @GET("api/progress/{progress_id}/cropHealthy")
    fun getCropHealthy(@Path("progress_id") progressId: Long): Call<Int>

    @GET("api/progress/{progress_id}/cropLight")
    fun getCropLight(@Path("progress_id") progressId: Long): Call<Int>

    @GET("api/progress/{progress_id}/cropEarlyBlight")
    fun getCropEarlyBlight(@Path("progress_id") progressId: Long): Call<Int>
}


