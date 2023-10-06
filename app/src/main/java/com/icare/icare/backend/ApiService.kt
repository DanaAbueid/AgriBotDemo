package com.icare.icare.backend

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService(private val accessToken: String?, private val baseUrl: String) {
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestWithToken = if (accessToken != null) {
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .build()
            } else {
                originalRequest
            }
            chain.proceed(requestWithToken)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) // Use the provided BASE_URL
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
