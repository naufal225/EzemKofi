package com.example.ezemkofi.data.models


import com.google.gson.annotations.SerializedName

data class CoffeePurchased(
    @SerializedName("coffee")
    val coffee: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("qty")
    val qty: Int,
    @SerializedName("size")
    val size: String
)