package com.example.ezemkofi.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ezemkofi.data.models.TransactionRequestItem
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.RetrofitInstance
import com.example.ezemkofi.data.repository.TransactionRepository
import com.example.ezemkofi.ui.viewmodel.base.BaseViewModel

class TransactionViewModel(application: Application) : BaseViewModel(application) {
    private val transactionInterface = RetrofitInstance.getTransactionInterface(application.applicationContext)
    private val transactionRepository = TransactionRepository(transactionInterface)

    private var _transaction = MutableLiveData<NetworkResponse<String>>()
    val transaction = _transaction

    fun checkout(transactionRequest: List<TransactionRequestItem>) {
        executeApiCall(_transaction, {transactionRepository.transaction(transactionRequest)})
    }
}