package com.example.ezemkofi.ui.viewmodel

import android.app.Application
import androidx.compose.foundation.text.BasicText
import androidx.lifecycle.MutableLiveData
import com.example.ezemkofi.data.models.LoginRequest
import com.example.ezemkofi.data.models.RegisterRequest
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.RetrofitInstance
import com.example.ezemkofi.data.repository.AuthRepository
import com.example.ezemkofi.ui.viewmodel.base.BaseViewModel

class AuthViewModel(private val application: Application) : BaseViewModel(application) {
    val authInterface = RetrofitInstance.getAuthInstance(application.applicationContext)
    val authRepository = AuthRepository(authInterface)

    val _authResult = MutableLiveData<NetworkResponse<String>>()

    var authResult = _authResult

    fun login(loginRequest: LoginRequest) {
        executeApiCall(_authResult, {authRepository.login(loginRequest)})
    }

    fun register(registerRequest: RegisterRequest) {
        executeApiCall(_authResult, {authRepository.register(registerRequest)})
    }
}