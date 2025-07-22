package com.example.ezemkofi.data.models


import com.google.gson.annotations.SerializedName

data class TransactionRequestItem(
    @SerializedName("coffeeId")
    val coffeeId: Int,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("size")
    val size: String
)