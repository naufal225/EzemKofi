package com.example.ezemkofi.data.remote

import com.example.ezemkofi.data.models.TransactionRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface TransactionInterface {
    @GET("checkout")
    suspend fun checkout(@Body transactionRequest: TransactionRequest) : Response<String>
}