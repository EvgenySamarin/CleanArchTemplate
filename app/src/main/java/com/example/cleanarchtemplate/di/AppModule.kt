package com.example.cleanarchtemplate.di

import android.content.Context
import androidx.annotation.Keep
import com.example.cleanarchtemplate.data.cache.database.AppRoomDatabase
import com.example.cleanarchtemplate.data.remote.example.ExampleRemote
import com.example.cleanarchtemplate.data.repository.example.ExampleRepository
import com.example.cleanarchtemplate.data.repository.example.ExampleRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Top app level dagger module. Provides base dependencies like as context and repositories
 *
 * @author EvgenySamarin
 * @since 20190905 v5
 */
@Module
@Keep
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    /** provide dependency example repository */
    @Provides
    @Singleton
    fun provideExampleRepository(
        remote: ExampleRemote, database: AppRoomDatabase
    ): ExampleRepository = ExampleRepositoryImpl(remote, database)

}