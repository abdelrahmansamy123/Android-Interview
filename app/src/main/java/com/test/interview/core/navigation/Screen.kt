package com.test.interview.core.navigation

sealed class Screen(
    val route: String
) {
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object Home : Screen("home")
}