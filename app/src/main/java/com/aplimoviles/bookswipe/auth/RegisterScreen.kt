package com.aplimoviles.bookswipe.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.aplimoviles.bookswipe.model.Usuario
import com.aplimoviles.bookswipe.navigation.Screen
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

@Composable
fun RegisterScreen(auth: FirebaseAuth, database: DatabaseReference, navController: NavController) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.Gray
                )
            })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email, contentDescription = null, tint = Color.Gray
                )
            })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = null,
                    tint = Color.Gray
                )
            })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = null, tint = Color.Gray
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Alternar visibilidad de la contraseña",
                        tint = Color.Gray
                    )
                }
            })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repite la contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = null, tint = Color.Gray
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Alternar visibilidad de la contraseña",
                        tint = Color.Gray
                    )
                }
            })
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                when {
                    name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() -> {
                        Toast.makeText(
                            context, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT
                        ).show()
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(context, "El email no es válido", Toast.LENGTH_SHORT).show()
                    }

                    phone.length != 9 -> {
                        Toast.makeText(
                            context, "El teléfono debe tener 9 caracteres", Toast.LENGTH_SHORT
                        ).show()
                    }

                    password.length < 6 -> {
                        Toast.makeText(
                            context,
                            "La contraseña debe tener 6 o más caracteres",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    !password.equals(repeatPassword) -> {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { authTask ->
                                if (authTask.isSuccessful) {
                                    val uid = authTask.result?.user?.uid
                                    if (uid != null) {
                                        val usuarioRegistrado = Usuario(
                                            nombre = name,
                                            correo = email,
                                            telefono = phone
                                        )
                                        database.child("usuario").child(uid)
                                            .setValue(usuarioRegistrado).addOnSuccessListener {
                                                Toast.makeText(
                                                    context,
                                                    "Usuario registrado en BD",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                navController.navigate(Screen.Login.route) {
                                                    popUpTo(Screen.Register.route) {
                                                        inclusive = true
                                                    }
                                                }
                                            }.addOnFailureListener { dbError ->
                                                Toast.makeText(
                                                    context,
                                                    "Ocurrió un error al registrarte: ${dbError.message}",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                auth.currentUser?.delete()
                                                dbError.printStackTrace()
                                            }
                                    }
                                } else {
                                    val errorMessage = when (authTask.exception) {
                                        is FirebaseAuthUserCollisionException -> "El correo ya está registrado"
                                        is FirebaseAuthWeakPasswordException -> "La contraseña no es segura"
                                        is FirebaseAuthInvalidCredentialsException -> "Formato de correo inválido"
                                        is FirebaseAuthInvalidUserException -> "Usuario deshabilitado, eliminado o corrupto"
                                        else -> "Error en el registro, inténtelo de nuevo"
                                    }
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¿Ya tienes una cuenta?", color = Color.Gray
            )
            TextButton(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}
