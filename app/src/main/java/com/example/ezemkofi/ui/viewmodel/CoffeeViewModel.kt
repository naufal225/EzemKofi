package com.example.ezemkofi.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ezemkofi.data.models.Category
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.RetrofitInstance
import com.example.ezemkofi.data.repository.CoffeeRepository
import com.example.ezemkofi.ui.viewmodel.base.BaseViewModel

class CoffeeViewModel(application: Application) : BaseViewModel(application) {
    private val coffeeInterface = RetrofitInstance.getCoffeeInterface(application.applicationContext)
    private val coffeeRepository = CoffeeRepository(coffeeInterface)

    private val _coffees = MutableLiveData<NetworkResponse<List<Coffee>>>()
    var coffees = _coffees

    private val _categories = MutableLiveData<NetworkResponse<List<Category>>>()
    var cateories = _categories

    private val _topPicks = MutableLiveData<NetworkResponse<List<Coffee>>>()
    var topPicks = _topPicks

    private val _coffee = MutableLiveData<NetworkResponse<Coffee>>()
    var coffee = _coffee

    fun getAllCoffee() {
        executeApiCall(_coffees, {coffeeRepository.getAllCoffee()})
    }

    fun getAllCategory() {
        executeApiCall(_categories, {coffeeRepository.getCoffeeCategory()})
    }

    fun getAllTopPick() {
        executeApiCall(_topPicks, {coffeeRepository.getTopPickCoffee()})
    }

    fun getCoffeeById(id : Int) {
        executeApiCall(_coffee, {coffeeRepository.getCoffeeById(id)})
    }
}