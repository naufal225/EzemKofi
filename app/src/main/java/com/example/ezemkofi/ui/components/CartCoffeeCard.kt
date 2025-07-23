package com.example.ezemkofi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ezemkofi.data.models.CartItem
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.RetrofitInstance
import com.example.ezemkofi.ui.navigation.Screen
import com.example.ezemkofi.ui.theme.EzemGray
import com.example.ezemkofi.ui.theme.EzemGreen
import com.example.ezemkofi.ui.theme.EzemWhite
import com.example.ezemkofi.ui.theme.Poppins


@Composable
fun CartCoffeeCard(cartItem: CartItem, onAddQuantity : () -> Unit, onDecreaseQuantity : () -> Unit, totalPrice : Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = RetrofitInstance.IMAGE_URL + cartItem.imagePath,
                contentDescription = cartItem.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(90.dp).clip(CircleShape)
            )

        }

        Spacer(Modifier.width(18.dp))

        Column(
            modifier = Modifier.weight(1f).padding(12.dp)
        ) {
            Text(
                text = cartItem.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Poppins
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = cartItem.category,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Poppins,
                color = EzemGray
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = cartItem.size,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Poppins,
                color = EzemGray
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.padding(3.dp).background(EzemWhite, CircleShape).border(1.dp, EzemGray, CircleShape),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        enabled = cartItem.quantity > 0,
                        onClick = onDecreaseQuantity
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "remove"
                        )
                    }

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = cartItem.quantity.toString(),
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        color = Color.Black
                    )

                    Spacer(Modifier.width(6.dp))

                    IconButton(
                        onClick = onAddQuantity
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "add"
                        )
                    }
                }

                Text(
                    text = "$%.2f".format(totalPrice),
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    fontFamily = Poppins
                )
            }

        }

    }}
