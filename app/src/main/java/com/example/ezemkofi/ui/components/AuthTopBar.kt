package com.example.ezemkofi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ezemkofi.R
import com.example.ezemkofi.ui.theme.EzemWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTobBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth().background(EzemWhite).padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_green_with_icon),
                    contentDescription = "logo",
                    modifier = Modifier.width(200.dp)
                )
            }
        }
    )
}