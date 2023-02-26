package com.example.bookfinder

import android.app.Application
import com.example.bookfinder.data.AppContainer

class BookApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}