package com.example.trackercriptocurrency.retrofit

import com.example.trackercriptocurrency.model.CoinModel
import com.example.trackercriptocurrency.model.ImageModel
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Headers
import retrofit2.http.Part
import kotlin.collections.ArrayList


interface RetrofitInterface {

    @Headers("X-CoinAPI-Key: 7F9937D3-4E7C-4B69-9D62-458E9F00AFD0")
    @GET("assets")
    fun getCoins(): Observable<ArrayList<CoinModel>>


    @Headers("X-CoinAPI-Key: 7F9937D3-4E7C-4B69-9D62-458E9F00AFD0")
    @GET("assets/icons/{60}")
    fun getImage(): Observable<ArrayList<ImageModel>>


}