package com.aplimoviles.bookswipe

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class MyApplication: Application() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        auth = FirebaseAuth.getInstance()
    }
}