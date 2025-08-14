package com.runner.cat

import android.app.Application
import com.runner.cat.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GameApp)
            modules(
                koinModule
            )
        }
    }
}