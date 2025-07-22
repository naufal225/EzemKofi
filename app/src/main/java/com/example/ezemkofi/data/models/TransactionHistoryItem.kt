package com.example.ezemkofi.data.models


import com.google.gson.annotations.SerializedName

data class TransactionHistoryItem(
    @SerializedName("coffeePurchased")
    val coffeePurchased: List<CoffeePurchased>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("transactionDate")
    val transactionDate: String,
    @SerializedName("userId")
    val userId: Int
)