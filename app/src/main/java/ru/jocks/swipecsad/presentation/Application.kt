package ru.jocks.swipecsad.presentation
import android.app.Application
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.jocks.data.di.dataModule
import ru.jocks.swipecsad.presentation.di.presentationModule
import timber.log.Timber

class Application() : Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("96832af2-ada2-4b08-922f-904309a622e8")

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@Application)
            modules(presentationModule, dataModule)
        }
    }
}