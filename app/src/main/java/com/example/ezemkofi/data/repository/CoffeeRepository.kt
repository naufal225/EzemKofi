package com.example.ezemkofi.data.repository

import com.example.ezemkofi.data.remote.CoffeeInterface

class CoffeeRepository(private val coffeeInterface: CoffeeInterface) {
    suspend fun getAllCoffee() = coffeeInterface.getAllCoffee()
    suspend fun getCoffeeById(id : Int) = coffeeInterface.getCoffeeById(id)
    suspend fun getTopPickCoffee() = coffeeInterface.getTopPicksCoffee()
    suspend fun getCoffeeCategory() = coffeeInterface.getCoffeeCategory()
}