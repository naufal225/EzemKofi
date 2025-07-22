package com.example.ezemkofi.data.models


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)