package com.jetbrains.bs23_kmp

import android.app.Application
import com.jetbrains.bs23_kmp.di.initKoin

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
