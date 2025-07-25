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
import com.example.ezemkofi.ui.screens.main.CartScreen
import com.example.ezemkofi.ui.screens.main.DetailCoffeeScreen
import com.example.ezemkofi.ui.screens.main.HomeScreen
import com.example.ezemkofi.ui.screens.main.SearchScreen
import com.example.ezemkofi.ui.viewmodel.AuthViewModel
import com.example.ezemkofi.ui.viewmodel.CoffeeViewModel
import com.example.ezemkofi.ui.viewmodel.TransactionViewModel
import kotlin.reflect.typeOf


fun NavGraphBuilder.mainNavigation(navController: NavHostController, coffeeViewModel: CoffeeViewModel, transactionViewModel: TransactionViewModel) {
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
                navArgument("id") {type = NavType.IntType},
                navArgument("category") {type = NavType.StringType}
            )
        ) { backstack ->
            val id = backstack.arguments?.getInt("id") ?: 0
            val category = backstack.arguments?.getString("category") ?: ""

            DetailCoffeeScreen(navController, coffeeViewModel, id, category)
        }

        composable(
            route = Screen.Cart.route
        ) {
            CartScreen(navController, transactionViewModel)
        }

        composable(
            route = Screen.Search.route
        ) {
            SearchScreen(navController, coffeeViewModel)
        }

    }
}