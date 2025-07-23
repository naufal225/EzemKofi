package com.example.ezemkofi.data.remote

import com.example.ezemkofi.data.models.TransactionRequestItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionInterface {
    @POST("checkout")
    suspend fun checkout(@Body transactionRequest: List<TransactionRequestItem>) : Response<String>
}