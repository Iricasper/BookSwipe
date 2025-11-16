package com.aplimoviles.bookswipe.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aplimoviles.bookswipe.navigation.Screen
import com.aplimoviles.bookswipe.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(auth: FirebaseAuth, navController: NavController) {
    val context = LocalContext.current
    val user = auth.currentUser

    val keepLoggedIn = remember {
        mutableStateOf(SessionManager.getKeepLoggedIn(context))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenid@ a BookSwipe, ${user?.email}",
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Mantener sesión iniciada")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = keepLoggedIn.value, onCheckedChange = {
                    keepLoggedIn.value = it
                    SessionManager.setKeepLoggedIn(context, it)
                })
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                SessionManager.setKeepLoggedIn(context, false)
                auth.signOut()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}