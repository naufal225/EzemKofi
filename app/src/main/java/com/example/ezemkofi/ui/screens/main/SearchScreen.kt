package com.example.ezemkofi.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.SharedPrefsManager
import com.example.ezemkofi.ui.components.TopPickCoffeeCard
import com.example.ezemkofi.ui.theme.EzemBlack
import com.example.ezemkofi.ui.theme.EzemGray
import com.example.ezemkofi.ui.theme.EzemWhite
import com.example.ezemkofi.ui.theme.Poppins
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel

@Composable
fun SearchScreen(navController: NavController, coffeeViewModel: CoffeeViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val coffees by coffeeViewModel.coffees.observeAsState()

    val context = LocalContext.current
    val sharedPrefsManager = SharedPrefsManager(context)

    LaunchedEffect(Unit) {
        coffeeViewModel.getAllCoffee()
        coffeeViewModel.getAllCategory()
        coffeeViewModel.getAllTopPick()
    }


    val filteredCoffee = remember(coffees, searchQuery) {
        when (coffees) {
            is NetworkResponse.SUCCESS -> {
                val data: List<Coffee> = (coffees as NetworkResponse.SUCCESS).data
                data.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
            }

            else -> emptyList()
        }
    }

    Scaffold(
        containerColor = EzemWhite,

        ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
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
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {searchQuery = it},
                        placeholder = {
                            Text("Find your prefect coffee", color = EzemGray)
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        shape = CircleShape
                    )
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Search Result",
                    fontFamily = Poppins, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp)
                )

                Spacer(Modifier.height(6.dp))

                when(coffees) {
                    is NetworkResponse.SUCCESS -> {
                        if(filteredCoffee.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth().padding(12.dp)
                            ) {
                                items(filteredCoffee) {
                                    TopPickCoffeeCard(it, navController)
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "There is no coffee found",
                                    color = EzemGray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = Poppins,
                                )
                            }
                        }

                    }
                    else -> {

                    }
                }
            }


        }
    }

}