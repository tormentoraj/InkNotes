package com.inknotes.inknotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InkNotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide components here
    }
}