package com.example.ezemkofi.ui.screens.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ezemkofi.data.models.CartItem
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.NetworkResponse
import com.example.ezemkofi.data.remote.RetrofitInstance
import com.example.ezemkofi.data.remote.SharedPrefsManager
import com.example.ezemkofi.ui.theme.EzemBlack
import com.example.ezemkofi.ui.theme.EzemGray
import com.example.ezemkofi.ui.theme.EzemGreen
import com.example.ezemkofi.ui.theme.EzemWhite
import com.example.ezemkofi.ui.theme.Poppins
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCoffeeScreen(navController: NavController, coffeeViewModel: CoffeeViewModel, id : Int, category: String) {
    val coffee by coffeeViewModel.coffee.observeAsState()

    LaunchedEffect(Unit) {
        coffeeViewModel.getCoffeeById(id)
    }

    val context = LocalContext.current
    val sharedPreferences = SharedPrefsManager(context)
    var quantity by remember { mutableIntStateOf(0) }

    var selectedSize by remember { mutableStateOf(CoffeeSize.MEDIUM) }

    val scale = remember(selectedSize) {
        when(selectedSize) {
            CoffeeSize.SMALL -> 0.85f
            CoffeeSize.MEDIUM -> 1f
            CoffeeSize.LARGE -> 1.15f
        }
    }

    val animatedScale by animateFloatAsState(scale, label = "scale")
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(animatedScale) {
        rotation.snapTo(rotation.value)
        rotation.animateTo(
            targetValue = rotation.value + 360f,
            animationSpec = tween(durationMillis = 1400, easing = EaseInOut)
        )
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        Modifier.padding(12.dp).background(EzemWhite, CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = EzemGray,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(EzemGreen),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Detail",
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            fontFamily = Poppins,
                            modifier = Modifier.offset(x = (-30).dp)
                        )
                    }

                }
            )
        }
    ){ padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp)
                        .background(EzemGreen, RoundedCornerShape(bottomEnd = 35.dp, bottomStart = 35.dp))
                        .clip(RoundedCornerShape(bottomEnd = 35.dp, bottomStart = 35.dp))
                )
            }

            when(coffee) {
                is NetworkResponse.SUCCESS -> {
                    val data = (coffee as NetworkResponse.SUCCESS<Coffee>).data
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 180.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        AsyncImage(
                            model = RetrofitInstance.IMAGE_URL + data.imagePath,
                            contentDescription = data.name,
                            modifier = Modifier
                                .size(300.dp)
                                .clip(CircleShape)
                                .graphicsLayer(
                                    scaleX = animatedScale,
                                    scaleY = animatedScale,
                                    rotationZ = rotation.value
                                )
                                .zIndex(1f)
                        )

                        SizeButton(
                            label = "S",
                            selected = selectedSize == CoffeeSize.SMALL,
                            onClick = {selectedSize = CoffeeSize.SMALL},
                            modifier = Modifier.offset(x = (-130).dp, y = 25.dp).graphicsLayer(
                                rotationZ = 45f
                            )
                        )

                        SizeButton(
                            label = "M",
                            selected = selectedSize == CoffeeSize.MEDIUM,
                            onClick = {selectedSize = CoffeeSize.MEDIUM},
                            modifier = Modifier.offset(x = (0).dp, y = 55.dp).graphicsLayer(
                                rotationZ = 0f
                            )
                        )

                        SizeButton(
                            label = "L",
                            selected = selectedSize == CoffeeSize.LARGE,
                            onClick = {selectedSize = CoffeeSize.LARGE},
                            modifier = Modifier.offset(x = (130).dp, y = 25.dp).graphicsLayer(
                                rotationZ = -45f
                            )
                        )

                        PriceCircle(
                            label = data.price.toString(),
                            modifier = Modifier.offset(x = (180).dp, y = (-60).dp)
                        )

                    }

                } else -> {

                }
            }

            when(coffee) {
                is  NetworkResponse.SUCCESS -> {
                    val data = (coffee as NetworkResponse.SUCCESS<Coffee>).data
                    Spacer(Modifier.height(25.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(12.dp)

                    ) {
                        Text(
                            text = data.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = data.description ?: "",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = Poppins,
                            color = EzemGray
                        )

                        Spacer(Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val basePrice = data.price

                            val price = remember(scale) {
                                basePrice * scale
                            }

                            Text(
                                text = "$ %.2f".format(price),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = Poppins,
                                color = Color.Black
                            )

                            Row(
                                modifier = Modifier.padding(3.dp).background(EzemWhite, CircleShape).border(1.dp, EzemGray, CircleShape),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(
                                    enabled = quantity > 0,
                                    onClick = {
                                        quantity -= 1
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "remove"
                                    )
                                }

                                Spacer(Modifier.width(6.dp))

                                Text(
                                    text = quantity.toString(),
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = Poppins,
                                    color = Color.Black
                                )

                                Spacer(Modifier.width(6.dp))

                                IconButton(
                                    onClick = {
                                        quantity += 1
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "add"
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(EzemGreen),
                                modifier = Modifier.fillMaxWidth().padding(12.dp),
                                onClick = {
                                    sharedPreferences.addToCart(
                                        CartItem(
                                            id = data.id,
                                            name = data.name,
                                            description = data.description ?: "Threre is no description" ,
                                            price = data.price,
                                            imagePath = data.imagePath,
                                            size = selectedSize.name[0].toString(),
                                            quantity = quantity,
                                            category = category
                                        )
                                    )
                                },
                                enabled = quantity > 0
                            ) {
                                Text(
                                    text = "Add To Cart",
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = Poppins,
                                    color = Color.White

                                )
                            }
                        }
                    }

                }
                else -> {

                }
            }

        }
    }
}

enum class CoffeeSize {SMALL, MEDIUM, LARGE}

@Composable
fun SizeButton(label : String, selected : Boolean, onClick : () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = if(selected) EzemGreen else EzemWhite)
            .border(1.dp, EzemBlack, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if(selected) Color.White else EzemGreen,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun PriceCircle(label : String, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(EzemGreen)
            .border(1.dp, EzemBlack, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}