package ca.tetervak.dicegame

import android.app.Application
import ca.tetervak.dicegame.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DiceGameApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DiceGameApplication)
            modules(appModule)
        }
    }
}