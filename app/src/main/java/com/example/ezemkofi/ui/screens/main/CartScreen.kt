package com.example.ezemkofi.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ezemkofi.data.models.CartItem
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.SharedPrefsManager
import com.example.ezemkofi.ui.components.CartCoffeeCard
import com.example.ezemkofi.ui.components.TopPickCoffeeCard
import com.example.ezemkofi.ui.theme.EzemBlack
import com.example.ezemkofi.ui.theme.EzemGray
import com.example.ezemkofi.ui.theme.EzemGreen
import com.example.ezemkofi.ui.theme.EzemWhite
import com.example.ezemkofi.ui.theme.Poppins
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, coffeeViewModel: CoffeeViewModel) {
    var listOfCoffee = remember { mutableStateListOf<CartItem>() }

    val context = LocalContext.current
    val sharedPrefsManager = SharedPrefsManager(context)

    LaunchedEffect(Unit) {
        val data = sharedPrefsManager.getCartItems()
        data.forEach {
            listOfCoffee.add(it)
        }
    }

    Scaffold(
        containerColor = EzemWhite,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        Modifier.padding(12.dp).background(EzemWhite, CircleShape).border(1.dp, Color.Black, CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = EzemBlack,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(EzemWhite),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Your Cart",
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontFamily = Poppins,
                            modifier = Modifier.offset(x = (-30).dp)
                        )
                    }

                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(listOfCoffee.size > 0) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(listOfCoffee) {
                            CartCoffeeCard(it,
                                onDecreaseQuantity = {
                                    if(it.quantity > 1) {
                                        val index = listOfCoffee.indexOf(it)
                                        val updatedItem = it.copy(quantity = it.quantity - 1)
                                        listOfCoffee[index] = updatedItem
                                        sharedPrefsManager.updateQuantity(it.id, it.size, updatedItem.quantity)
                                    } else {
                                        listOfCoffee.remove(it)
                                        sharedPrefsManager.removeFromCart(it.id, it.size)
                                    }
                                },
                                onAddQuantity = {
                                    val index = listOfCoffee.indexOf(it)
                                    val updatedItem = it.copy(quantity = it.quantity + 1)
                                    listOfCoffee[index] = updatedItem
                                    sharedPrefsManager.updateQuantity(it.id, it.size, updatedItem.quantity)

                                })
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                       Text(
                           text = "Your cart is empty",
                           color = EzemGray,
                           fontSize = 16.sp,
                           fontWeight = FontWeight.Bold,
                           fontFamily = Poppins
                       )
                    }
                }

            }
        }
    }
}