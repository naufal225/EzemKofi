package com.example.ezemkofi.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.RetrofitInstance
import com.example.ezemkofi.ui.navigation.Screen
import com.example.ezemkofi.ui.theme.EzemGreen
import com.example.ezemkofi.ui.theme.Poppins
@Composable
fun CoffeeCard(coffee: Coffee, navController: NavController) {
    Box(
        modifier = Modifier
            .width(240.dp)
            .height(240.dp)
            .background(EzemGreen, shape = RoundedCornerShape(24.dp))
            .clickable {
                navController.navigate(Screen.Coffee.passId(coffee.id)) {
                    popUpTo(Screen.Home.route) {inclusive = false}
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            // Gambar kopi bulat yang mengambang
            Box(
                modifier = Modifier
                    .offset(y = (-90).dp)
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = RetrofitInstance.IMAGE_URL + coffee.imagePath,
                    contentDescription = coffee.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Nama kopi
            Text(
                text = coffee.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Poppins,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(Color(0xFF3B914A), RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = coffee.rating.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = Poppins
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Harga
            Text(
                text = "$ %.2f".format(coffee.price),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = Poppins
            )
        }
    }
}
