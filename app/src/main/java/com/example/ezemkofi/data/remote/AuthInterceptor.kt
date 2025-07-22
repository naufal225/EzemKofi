package com.example.ezemkofi.data.remote

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sharedPrefsManager: SharedPrefsManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPrefsManager.getToken()
        val request = if(token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json")
                .build()
        } else {
            chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
        }
        return chain.proceed(request)
    }

}