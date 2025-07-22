package com.example.ezemkofi.data.remote

sealed class NetworkResponse<out T>() {
    data class SUCCESS<T>(val data : T) : NetworkResponse<T>()
    data class ERROR(val message : String) : NetworkResponse<Nothing>()
    data object LOADING : NetworkResponse<Nothing>()
}