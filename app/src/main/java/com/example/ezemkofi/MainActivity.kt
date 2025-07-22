package com.example.ezemkofi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ezemkofi.ui.navigation.RootNavGraph
import com.example.ezemkofi.ui.screens.auth.LoginScreen
import com.example.ezemkofi.ui.theme.EzemKofiTheme
import com.example.ezemkofi.ui.viewmodel.AuthViewModel
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val authViewModel : AuthViewModel by viewModels()
        val coffeeViewModel : CoffeeViewModel by viewModels()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            EzemKofiTheme {
                RootNavGraph(navController, authViewModel, coffeeViewModel)
            }
        }
    }

}