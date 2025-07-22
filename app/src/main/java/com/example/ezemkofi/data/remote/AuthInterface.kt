package com.example.ezemkofi.data.remote

import com.example.ezemkofi.data.models.LoginRequest
import com.example.ezemkofi.data.models.RegisterRequest
import com.example.ezemkofi.data.models.TransactionHistory
import com.example.ezemkofi.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthInterface {
    @POST("auth")
    suspend fun login(@Body authRequest: LoginRequest) : Response<String>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest) : Response<String>

    @GET("me")
    suspend fun me() : Response<User>

    @GET("me/transaction")
    suspend fun myTransaction() : Response<TransactionHistory>
}