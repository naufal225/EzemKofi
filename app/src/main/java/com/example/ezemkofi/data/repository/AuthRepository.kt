package com.example.ezemkofi.data.repository

import com.example.ezemkofi.data.models.LoginRequest
import com.example.ezemkofi.data.models.RegisterRequest
import com.example.ezemkofi.data.remote.AuthInterface

class AuthRepository(private val authInterface: AuthInterface) {
    suspend fun login(loginRequest: LoginRequest) = authInterface.login(loginRequest)
    suspend fun register(registerRequest: RegisterRequest) = authInterface.register(registerRequest)
    suspend fun me() = authInterface.me()
    suspend fun myTransaction() = authInterface.myTransaction()
}