package com.example.ezemkofi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.ezemkofi.ui.viewmodel.AuthViewModel
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel
import com.example.ezemkofi.ui.viewmodel.TransactionViewModel

@Composable
fun RootNavGraph(navController: NavHostController, authViewModel: AuthViewModel, coffeeViewModel: CoffeeViewModel, transactionViewModel: TransactionViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.route
    ) {
        authNavGraph(navController, authViewModel)
        mainNavigation(navController, coffeeViewModel, transactionViewModel)
    }
}