package com.example.ezemkofi.data.repository

import com.example.ezemkofi.data.models.TransactionRequestItem
import com.example.ezemkofi.data.remote.TransactionInterface

class TransactionRepository(private val transactionInterface: TransactionInterface) {
    suspend fun transaction(transactionRequest: List<TransactionRequestItem>) = transactionInterface.checkout(transactionRequest)
}