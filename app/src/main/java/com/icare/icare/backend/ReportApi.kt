package com.icare.icare.backend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import com.icare.icare.models.ReportRequestBody
import com.icare.icare.models.ReportDBO



interface ReportApi {
    @POST("api/report/save/{user_id}/{title}/{reason}/{reported}/{rep_text}")
    fun saveReport(
        @Path("user_id") userId: Long,
        @Path("title") title: String,
        @Path("reason") reason: String,
        @Path("reported") reported: String,
        @Path("rep_text") repText: String,
        @Body reportRequestBody: ReportRequestBody
    ): Call<Void>

    @GET("api/report/AllReports")
    fun getAllReports(): Call<List<ReportDBO>>

    @GET("api/report/notResolved/reports")
    fun getAllVisibleReports(): Call<List<ReportDBO>>

    @GET("api/report/reports/{reportId}")
    fun showReportById(@Path("reportId") reportId: Long): Call<String>

    @PUT("api/report/{report_id}/resolve")
    fun resolveReport(
        @Path("report_id") reportId: Long,
        @Query("reply") reply: String
    ): Call<Void>

    @DELETE("api/report/delete/{reportId}")
    fun deleteReport(@Path("reportId") reportId: Long): Call<Void>
}
