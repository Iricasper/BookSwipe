package com.aplimoviles.bookswipe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// si registro --> RegistroScreen
// si login -->
// si home

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "home"
    ) {
        composable("home") {
            // Pantalla home
        }
        composable("login") {
            // Pantalla login
        }
        composable("register") {
            // Pantalla register
        }
    }
}