package com.icare.icare.backend

import com.icare.icare.Request.ReportRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportApiService {
    @POST("/api/v1/report/save")
    fun submitReport(@Body reportRequestBody: ReportRequestBody): Call<Void>
}
