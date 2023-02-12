package com.example.plant_life.dataApi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://mocki.io"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PlantApiService {
    @GET("/v1/8a44a50e-2b81-4fa9-82d9-9adf0cc08dd4")
    suspend fun getInfo(): Response

    suspend fun getImage(@Query("page")page:Int):Response


}

object PlantApi{
    val retrofitServer : PlantApiService by lazy {
        retrofit.create(PlantApiService::class.java)
    }
}
