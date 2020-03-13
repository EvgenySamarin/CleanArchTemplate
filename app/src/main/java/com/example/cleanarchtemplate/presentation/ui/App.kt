package com.example.cleanarchtemplate.presentation.ui

import android.app.Application
import androidx.annotation.Keep
import com.example.cleanarchtemplate.di.AppModule
import com.example.cleanarchtemplate.di.CacheModule
import com.example.cleanarchtemplate.di.RemoteModule
import com.example.cleanarchtemplate.di.ViewModelModule
import com.example.cleanarchtemplate.presentation.ui.home.HomeActivity
import com.example.cleanarchtemplate.presentation.ui.home.fragments.main.LeftFragment
import dagger.Component
import timber.log.Timber
import javax.inject.Singleton

/**
 * The root file to initialize different libraries like Dagger, Timber etc. Also can contains
 * general app logic
 *
 * @author EvgenySamarin
 * @since 20200313 v1
 */
@Keep
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree()) //enable timber logging
        initAppComponent() //create Dagger DI
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}

/**
 * Base dependency injection class to provide different dependencies
 */
@Singleton
@Component(modules = [AppModule::class, CacheModule::class, RemoteModule::class, ViewModelModule::class])
interface AppComponent {
    //define classes to injection

    //fragments
    fun inject(fragment: LeftFragment)
}