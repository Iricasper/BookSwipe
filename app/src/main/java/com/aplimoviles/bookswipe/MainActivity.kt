package com.aplimoviles.bookswipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aplimoviles.bookswipe.navigation.AppNavigation
import com.aplimoviles.bookswipe.ui.theme.BookSwipeTheme
import com.aplimoviles.bookswipe.utils.SessionManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        database = Firebase.database("https://loginfirebase-34f3b-default-rtdb.europe-west1.firebasedatabase.app").reference

        if (!SessionManager.getKeepLoggedIn(this)) {
            auth.signOut()
        }

        setContent {
            BookSwipeTheme {
                AppNavigation(auth, database)
            }
        }
    }
}