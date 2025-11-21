package com.aplimoviles.bookswipe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aplimoviles.bookswipe.ui.screens.auth.HomeScreen
import com.aplimoviles.bookswipe.ui.screens.auth.LoginScreen
import com.aplimoviles.bookswipe.ui.screens.auth.RegisterScreen
import com.aplimoviles.bookswipe.ui.screens.element.LibroFormScreen
import com.aplimoviles.bookswipe.ui.screens.element.LibroListScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object LibroList : Screen("libroList")
    object LibroForm : Screen("libroForm")
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
            RegisterScreen(auth, database, navController)
        }
        composable(Screen.LibroList.route) {
            LibroListScreen(auth, database, navController)
        }
        composable(Screen.LibroForm.route) {
            LibroFormScreen(auth, database, navController)
        }
    }
}