package com.example.ezemkofi.data.models


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)