package com.example.ezemkofi.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {
    const val API_URL = "http://10.0.2.2:5000/api/"
    const val IMAGE_URL = "http://10.0.2.2:5000/images/"

    fun getRetrofit(context : Context) : Retrofit {
        val sharedPrefsManager = SharedPrefsManager(context)
        val authInterceptor = AuthInterceptor(sharedPrefsManager)

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getAuthInstance(context: Context) : AuthInterface {
        return getRetrofit(context).create(AuthInterface::class.java)
    }

    fun getCoffeeInterface(context: Context) : CoffeeInterface {
        return getRetrofit(context).create(CoffeeInterface::class.java)
    }

    fun getTransactionInterface(context: Context) : TransactionInterface {
        return getRetrofit(context).create(TransactionInterface::class.java)
    }
}