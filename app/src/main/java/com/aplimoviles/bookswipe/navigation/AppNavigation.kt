package com.aplimoviles.bookswipe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aplimoviles.bookswipe.auth.HomeScreen
import com.aplimoviles.bookswipe.auth.LoginScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
}

@Composable
fun AppNavigation(auth: FirebaseAuth, database: DatabaseReference) {
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = if (auth.currentUser != null) Screen.Home.route else Screen.Login.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(auth, navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(auth, navController)
        }
        composable(Screen.Register.route) {
//            RegisterScreen(auth, database, navController)
        }
    }
}