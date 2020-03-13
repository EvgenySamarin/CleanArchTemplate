package com.example.cleanarchtemplate.di

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Keep
import com.example.cleanarchtemplate.data.cache.database.AppRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides dependencies of cache layer
 *
 * @author EvgenySamarin
 * @since 20190905 v1
 */
@Module
@Keep
class CacheModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAppRoomDatabase(context: Context): AppRoomDatabase =
        AppRoomDatabase.getDatabase(context)
}