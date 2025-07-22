package com.example.ezemkofi.data.remote

import android.content.Context
import com.example.ezemkofi.data.models.CartItem
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

    fun addToCart(context: Context, coffeeId: String, size: String, quantity: Int) {
        val sharedPref = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val cartJson = sharedPref.getString("cart_data", "{}")
        val jsonObj = JSONObject(cartJson ?: "{}")

        val key = "${coffeeId}_${size}"
        val existingQty = jsonObj.optInt(key, 0)

        jsonObj.put(key, existingQty + quantity)

        sharedPref.edit().putString("cart_data", jsonObj.toString()).apply()
    }

    fun decreaseFromCart(context: Context, coffeeId: String, size: String, quantity: Int) {
        val sharedPref = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val cartJson = sharedPref.getString("cart_data", "{}")
        val jsonObj = JSONObject(cartJson ?: "{}")

        val key = "${coffeeId}_${size}"
        val existingQty = jsonObj.optInt(key, 0)

        jsonObj.put(key, existingQty - quantity)

        sharedPref.edit().putString("cart_data", jsonObj.toString()).apply()
    }

    fun removeFromCart(context: Context, coffeeId: String, size: String, quantity: Int) {
        val sharedPref = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val cartJson = sharedPref.getString("cart_data", "{}")
        val jsonObj = JSONObject(cartJson ?: "{}")

        val key = "${coffeeId}_${size}"
        val existingQty = jsonObj.optInt(key, 0)

        jsonObj.put(key, existingQty + quantity)

        sharedPref.edit().putString("cart_data", jsonObj.toString()).apply()
    }

    fun getCartItems(context: Context): List<CartItem> {
        val sharedPref = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
        val cartJson = sharedPref.getString("cart_data", "{}")
        val jsonObj = JSONObject(cartJson ?: "{}")
        val items = mutableListOf<CartItem>()

        jsonObj.keys().forEach { key ->
            val quantity = jsonObj.getInt(key)
            if(quantity > 0) {
                val parts = key.split("_")
                if (parts.size == 2) {
                    val coffeeId = parts[0]
                    val size = parts[1]
                    items.add(CartItem(coffeeId, size, quantity))
                }
            }
        }
        return items
    }


}