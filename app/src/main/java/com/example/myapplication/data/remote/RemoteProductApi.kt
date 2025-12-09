package com.example.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class RemoteProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val category: String
)

interface RemoteProductApi {
    @GET("products")
    suspend fun getProducts(): List<RemoteProductDto>
}

object RemoteProductService {

    private const val BASE_URL = "https://fakestoreapi.com/"

    val api: RemoteProductApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteProductApi::class.java)
    }
}
