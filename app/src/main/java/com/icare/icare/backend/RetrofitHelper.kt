package com.icare.icare.backend
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val baseUrl = "http://34.224.41.160:8080/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}