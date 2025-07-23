package com.example.ezemkofi.ui.screens.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ezemkofi.R
import com.example.ezemkofi.data.models.Category
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.SharedPrefsManager
import com.example.ezemkofi.ui.components.CoffeeCard
import com.example.ezemkofi.ui.components.TopPickCoffeeCard
import com.example.ezemkofi.ui.navigation.Screen
import com.example.ezemkofi.ui.theme.EzemGray
import com.example.ezemkofi.ui.theme.EzemGreen
import com.example.ezemkofi.ui.theme.EzemWhite
import com.example.ezemkofi.ui.theme.Poppins
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, coffeeViewModel: CoffeeViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val coffees by coffeeViewModel.coffees.observeAsState()
    val categories by coffeeViewModel.cateories.observeAsState()
    val topPicks by coffeeViewModel.topPicks.observeAsState()

    val context = LocalContext.current
    val sharedPrefsManager = SharedPrefsManager(context)

    LaunchedEffect(Unit) {
        coffeeViewModel.getAllCoffee()
        coffeeViewModel.getAllCategory()
        coffeeViewModel.getAllTopPick()
    }

    var selectedCategory by remember { mutableStateOf("Americano") }

    val filteredCoffee = remember(coffees, selectedCategory, searchQuery) {
        when(coffees) {
            is NetworkResponse.SUCCESS -> {
                val data : List<Coffee> = (coffees as NetworkResponse.SUCCESS).data
                data.filter {
                    it.name.contains(searchQuery, ignoreCase = true) &&
                    it.category.contains(selectedCategory, ignoreCase = true)
                }
            }
            else -> emptyList()
        }
    }

    Scaffold(
        containerColor = EzemWhite,
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                        ) {
                            Column(
                                modifier = Modifier.weight(1f).padding(6.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Good Morning",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    fontFamily = Poppins
                                )

                                Spacer(Modifier.height(12.dp))

                                Text(
                                    text = sharedPrefsManager.getName() ?: "Amir",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    fontFamily = Poppins
                                )
                            }

                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.Cart.route) {
                                        popUpTo(Screen.Home.route) {
                                            inclusive = false
                                        }
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(EzemWhite)
                            ) {
                                Box(
                                    modifier = Modifier.size(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.shopping_bag),
                                        contentDescription = "shoppingBag",
                                        Modifier.size(28.dp),

                                        )
                                    if(sharedPrefsManager.getCartItems().isNotEmpty()) {
                                        Badge(
                                            containerColor = Color.Red,
                                            modifier = Modifier.clip(CircleShape).align(Alignment.TopEnd)
                                        ) {
                                            Text(
                                                text = sharedPrefsManager.getCartItems().count().toString(),
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                fontFamily = Poppins
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.background(Color.White).height(160.dp).shadow(elevation = 4.dp),
                colors = TopAppBarDefaults.topAppBarColors(EzemWhite)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Column {
                LazyColumn (
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                ) {
                    item {
                        Spacer(Modifier.height(18.dp))
                    }

                    item {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {searchQuery = it},
                            placeholder = {
                                Text("Find your prefect coffee", color = EzemGray)
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        navController.navigate(Screen.Search.route) {
                                            popUpTo(Screen.Home.route) {inclusive = false}
                                            launchSingleTop = true
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search"
                                    )
                                }

                            },
                            modifier = Modifier.height(50.dp).fillMaxWidth(),
                            shape = CircleShape
                        )
                    }

                    item {
                        Spacer(Modifier.height(6.dp))
                    }

                    item {
                        Text(
                            text = "Categories",
                            fontFamily = Poppins, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    item {
                        Spacer(Modifier.height(6.dp))
                    }

                    item {
                        when(categories) {
                            is NetworkResponse.SUCCESS -> {
                                val data = (categories as NetworkResponse.SUCCESS).data
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    items(data) {
                                        FilterChip(
                                            selected = selectedCategory == it.name,
                                            onClick = {
                                                if(selectedCategory == it.name) {
                                                    selectedCategory = ""
                                                } else {
                                                    selectedCategory = it.name
                                                }
                                            },
                                            label = {
                                                Text(
                                                    it.name,
                                                    color = if(selectedCategory == it.name) Color.White else EzemGreen
                                                )
                                            },
                                            modifier = Modifier.padding(6.dp),
                                            shape = RoundedCornerShape(8.dp),
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = EzemGreen
                                            )
                                        )
                                    }
                                }
                            }
                            else  -> {
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "There is no category found",
                                        color = EzemGray,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = Poppins,
                                    )
                                }
                            }
                        }
                    }

                    item {
                        when(coffees) {
                            is NetworkResponse.SUCCESS -> {
                                if (filteredCoffee.isNotEmpty()) {
                                    LazyRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        items(filteredCoffee) {
                                            Box(
                                                modifier = Modifier.padding(top = 90.dp).background(
                                                    Brush.horizontalGradient(
                                                        colors = listOf(Color.White, Color.Transparent)
                                                    )
                                                )
                                            ) {
                                                CoffeeCard(it,navController)
                                            }
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
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
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
                    }

                    item {
                        Spacer(Modifier.height(8.dp))
                    }

                    item {
                        Text(
                            text = "Top Picks",
                            fontFamily = Poppins, fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    item {
                        Spacer(Modifier.height(6.dp))
                    }

                    when(topPicks) {
                        is NetworkResponse.SUCCESS -> {
                            val data = (topPicks as NetworkResponse.SUCCESS).data
                            items(data) {
                                TopPickCoffeeCard(it, navController)
                            }
                        }
                        else -> {
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "There is no top pick coffee found",
                                        color = EzemGray,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = Poppins,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}