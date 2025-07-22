package com.example.ezemkofi.data.repository

import com.example.ezemkofi.data.models.TransactionRequest
import com.example.ezemkofi.data.remote.TransactionInterface

class TransactionInterface(private val transactionInterface: TransactionInterface) {
    suspend fun transaction(transactionRequest: TransactionRequest) = transactionInterface.checkout(transactionRequest)
}