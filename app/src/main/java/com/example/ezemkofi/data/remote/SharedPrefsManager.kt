package com.example.ezemkofi.data.remote

import android.content.Context
import com.example.ezemkofi.data.models.CartItem
import com.example.ezemkofi.data.models.Coffee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class SharedPrefsManager(private val context: Context) {
    val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)

    fun saveToken(token : String) {
        sharedPreferences.edit().putString("TOKEN", token).apply()
    }

    fun getToken() : String? {
        return sharedPreferences.getString("TOKEN", null)
    }

    fun saveName(name : String) {
        sharedPreferences.edit().putString("NAME", name).apply()
    }

    fun getName() : String? {
        return sharedPreferences.getString("NAME", null)
    }

    fun addToCart(cartCoffee: CartItem) {
        val gson = Gson()
        val json = sharedPreferences.getString("CART", "[]")
        val type = object : TypeToken<MutableList<CartItem>>() {}.type
        val cartList : MutableList<CartItem> = gson.fromJson(json, type)

        val existing = cartList.find { it.id == cartCoffee.id && it.size == cartCoffee.size }

        if(existing != null) {
            existing.quantity += cartCoffee.quantity
        } else {
            cartList.add(cartCoffee)
        }

        sharedPreferences.edit().putString("CART", gson.toJson(cartList)).apply()

    }

    fun getCartItems() : List<CartItem> {
        val gson = Gson()
        val json = sharedPreferences.getString("CART", "[]")
        val type = object : TypeToken<List<CartItem>>(){}.type
        return gson.fromJson(json, type)
    }

    fun updateQuantity(id: Int, size: String, newQuantity: Int) {
        val gson = Gson()
        val json = sharedPreferences.getString("CART", "[]")
        val type = object : TypeToken<MutableList<CartItem>>(){}.type
        val coffeeCart = gson.fromJson<MutableList<CartItem>>(json, type)

        val item = coffeeCart.find { it.id == id && it.size == size }

        if(item != null) {
            item.quantity = newQuantity
            sharedPreferences.edit().putString("CART", gson.toJson(coffeeCart)).apply()
        }
    }

    fun removeFromCart(id: Int, size: String) {
        val gson = Gson()
        val json = sharedPreferences.getString("CART", "[]")
        val type = object : TypeToken<MutableList<CartItem>>(){}.type
        val coffeeCart = gson.fromJson<MutableList<CartItem>>(json, type)

        val updatedList = coffeeCart.filterNot { it.id == id && it.size == size }

        sharedPreferences.edit().putString("CART", gson.toJson(updatedList)).apply()
    }


}