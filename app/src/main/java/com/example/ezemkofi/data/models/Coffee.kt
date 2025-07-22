package com.example.ezemkofi.data.models

import com.google.gson.annotations.SerializedName

data class Coffee (
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Double
)
