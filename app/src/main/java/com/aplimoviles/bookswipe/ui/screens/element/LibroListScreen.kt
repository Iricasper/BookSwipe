package com.aplimoviles.bookswipe.ui.screens.element

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.aplimoviles.bookswipe.model.Libro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun LibroListScreen(auth: FirebaseAuth, database: DatabaseReference, navController: NavController) {
    val context = LocalContext.current
    val user = auth.currentUser

    val librosRef = FirebaseDatabase.getInstance().getReference("libro")

    librosRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val libros = snapshot.children.mapNotNull {
                it.getValue(Libro::class.java)?.apply { isbn = it.key.toString() }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, "Error al cargar los libros", Toast.LENGTH_LONG).show()
        }
    }



    )

}