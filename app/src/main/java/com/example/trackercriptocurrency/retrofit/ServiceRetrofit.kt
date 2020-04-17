package com.example.trackercriptocurrency.retrofit

import com.example.trackercriptocurrency.common.Common.BASE_URL
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class ServiceRetrofit {
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(OkHttpClient().newBuilder().apply {
            connectTimeout(25, TimeUnit.SECONDS)
            readTimeout(25, TimeUnit.SECONDS)
            writeTimeout(25, TimeUnit.SECONDS)
        }.build())
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    val api: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)
}