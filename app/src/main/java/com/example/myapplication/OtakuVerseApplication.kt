package com.example.myapplication

import android.app.Application
import android.util.Log
import com.example.myapplication.ui.theme.config.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class OtakuVerseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("OtakuVerse", "🚀 Koin inicializando...")
        startKoin {
            androidLogger()
            androidContext(this@OtakuVerseApplication)
            module { appModules }
        }
    }
}