package com.aplimoviles.bookswipe.ui.screens.element

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Composable
fun LibroListScreen(auth: FirebaseAuth, database: DatabaseReference, navController: NavController) {
    val context = LocalContext.current
    val user = auth.currentUser

    val librosRef = FirebaseDatabase.getInstance().getReference("libro")
    database.child("libro")

}