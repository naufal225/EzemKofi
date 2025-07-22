package com.example.ezemkofi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ezemkofi.ui.screens.auth.LoginScreen
import com.example.ezemkofi.ui.screens.auth.RegisterScreen
import com.example.ezemkofi.ui.viewmodel.AuthViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController, authViewModel: AuthViewModel) {
    navigation(
        startDestination = Screen.Login.route,
        route = Screen.Auth.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(navController, authViewModel)
        }

        composable(
            route = Screen.Register.route
        ) {
            RegisterScreen(navController, authViewModel)
        }
    }
}