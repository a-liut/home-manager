package it.aliut.homemanager

import android.app.Application
import android.graphics.Color
import it.aliut.homemanager.di.netModule
import it.aliut.homemanager.di.repositoryModule
import it.aliut.homemanager.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import kotlin.random.Random

open class HomeManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startDI()
    }

    private fun startDI() {
        startKoin {
            androidContext(this@HomeManagerApplication)
            modules(provideModules())
        }
    }

    open fun provideModules() = listOf(netModule, repositoryModule, viewModelModule)
}