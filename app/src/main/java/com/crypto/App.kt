package com.crypto

import androidx.multidex.MultiDexApplication
import com.crypto.di.cryptoApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initDI()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initDI() {
        startKoin {
            androidContext(this@App)
            modules(cryptoApp)
        }
    }
}