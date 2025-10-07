package com.example.myapplication

import android.app.Application
import android.util.Log
import com.example.myapplication.config.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OtakuVerseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("OtakuVerse", "🚀 Koin inicializando...")
        startKoin {
            androidLogger()
            androidContext(this@OtakuVerseApplication)
            modules(appModules)
        }
    }

}