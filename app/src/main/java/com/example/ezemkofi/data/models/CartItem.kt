package com.example.ezemkofi.data.models

data class CartItem(
    val id: Int,
    val name: String,
    val description: String?,
    val imagePath: String,
    val size: String,
    val price: Double,
    var quantity: Int,
    var category: String
)
