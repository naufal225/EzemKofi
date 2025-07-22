package com.example.ezemkofi.ui.viewmodel.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ezemkofi.data.remote.NetworkResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    fun <T> executeApiCall(
        liveData: MutableLiveData<NetworkResponse<T>>,
        apiCall: suspend () -> retrofit2.Response<T>
    ) {
        liveData.postValue(NetworkResponse.LOADING)
        viewModelScope.launch {
            try {
                val response = apiCall()
                if(response.isSuccessful) {
                    response.body()?.let {
                        liveData.postValue(NetworkResponse.SUCCESS(it))
                        Log.d("Result ViewModel: ", response.body().toString())
                    } ?: {
                        liveData.postValue(NetworkResponse.ERROR("can't get the data"))
                        Log.d("Error ViewModel: ", response.message().toString())

                    }
                } else {

                    liveData.postValue(NetworkResponse.ERROR(response.message()))
                    Log.d("Error ViewModel: ", response.message().toString())

                }
            } catch (e : Exception) {
                liveData.postValue(NetworkResponse.ERROR("kesalahan teknis: "))
                Log.d("Error ViewModel: ", e.toString())

            }
        }
    }
}