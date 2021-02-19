package com.cedric.foodforfamily

import android.app.Application
import com.google.firebase.FirebaseApp

class FoodApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}