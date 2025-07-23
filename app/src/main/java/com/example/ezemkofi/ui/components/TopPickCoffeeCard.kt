package com.example.ezemkofi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ezemkofi.data.models.Coffee
import com.example.ezemkofi.data.remote.RetrofitInstance
import com.example.ezemkofi.ui.navigation.Screen
import com.example.ezemkofi.ui.theme.EzemGray
import com.example.ezemkofi.ui.theme.EzemGreen
import com.example.ezemkofi.ui.theme.EzemWhite
import com.example.ezemkofi.ui.theme.Poppins

@Composable
fun TopPickCoffeeCard(coffee: Coffee, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                navController.navigate(Screen.Coffee.passId(coffee.id, coffee.category)) {
                    popUpTo(Screen.Home.route) {inclusive = false}
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = RetrofitInstance.IMAGE_URL + coffee.imagePath,
                contentDescription = coffee.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp).clip(CircleShape)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .offset(y = 12.dp)
                    .background(EzemGreen, shape = RoundedCornerShape(13.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "rating",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = coffee.rating.toString(),
                    fontSize = 12.sp,
                    color = EzemWhite
                )
            }
        }

        Spacer(Modifier.width(18.dp))

        Column(
            modifier = Modifier.weight(1f).padding(12.dp)
        ) {
            Text(
                text = coffee.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Poppins
            )

            Spacer(Modifier.height(3.dp))

            Text(
                text = coffee.category,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Poppins,
                color = EzemGray
            )

            Spacer(Modifier.height(18.dp))

            Text(
                text = "$%.2f".format(coffee.price),
                fontSize = 16.sp,
                fontWeight = Bold,
                fontFamily = Poppins
            )
        }

    }
}