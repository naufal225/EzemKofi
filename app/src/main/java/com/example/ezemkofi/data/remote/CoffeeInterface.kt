package com.example.ezemkofi.data.remote

import com.example.ezemkofi.data.models.Category
import com.example.ezemkofi.data.models.Coffee
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeInterface {
    @GET("coffee")
    suspend fun getAllCoffee() : Response<List<Coffee>>

    @GET("coffee/{id}")
    suspend fun getCoffeeById(@Path("id") id : Int) : Response<Coffee>

    @GET("coffee/top-picks")
    suspend fun getTopPicksCoffee() : Response<List<Coffee>>

    @GET("coffee-category")
    suspend fun getCoffeeCategory() : Response<List<Category>>
}