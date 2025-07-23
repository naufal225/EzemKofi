package com.example.ezemkofi.ui.navigation

sealed class Screen(val route: String) {
    data object Auth : Screen("Auth")

    data object Login : Screen("Login")
    data object Register : Screen("Register")

    data object Main : Screen("Main")

    data object Home : Screen("Home" )
    data object Coffee : Screen("coffee/{id}/{category}") {
        fun passId(id: Int, category: String) : String = "coffee/${id}/${category}"
    }

    data object Cart : Screen("Cart")
    data object Search : Screen("Search")



}