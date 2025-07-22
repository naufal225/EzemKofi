package com.example.ezemkofi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.ezemkofi.ui.screens.auth.LoginScreen
import com.example.ezemkofi.ui.screens.auth.RegisterScreen
import com.example.ezemkofi.ui.screens.main.DetailCoffeeScreen
import com.example.ezemkofi.ui.screens.main.HomeScreen
import com.example.ezemkofi.ui.viewmodel.AuthViewModel
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel


fun NavGraphBuilder.mainNavigation(navController: NavHostController, coffeeViewModel: CoffeeViewModel) {
    navigation(
        startDestination = Screen.Home.route,
        route = Screen.Main.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController, coffeeViewModel)
        }

        composable(
            route = Screen.Coffee.route,
            arguments = listOf(
                navArgument("id") {type = NavType.IntType}
            )
        ) { backstack ->
            val id = backstack.arguments?.getInt("id") ?: 0

            DetailCoffeeScreen(navController, coffeeViewModel, id)
        }

    }
}