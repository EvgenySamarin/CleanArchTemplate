package com.example.cleanarchtemplate.di

import android.content.Context
import androidx.annotation.Keep
import com.example.cleanarchtemplate.BuildConfig
import com.example.cleanarchtemplate.data.remote.ApiService
import com.example.cleanarchtemplate.data.remote.ServiceGenerator
import com.example.cleanarchtemplate.data.remote.core.Request
import com.example.cleanarchtemplate.data.remote.example.ExampleRemote
import com.example.cleanarchtemplate.data.remote.example.ExampleRemoteImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Provides dependencies of remote layer
 *
 * @author EvgenySamarin
 * @since 20190905 v8
 */
@Module
@Keep
class RemoteModule {
    /** создаём экземпляр API с логированием или без */
    @Singleton
    @Provides
    fun provideApiService(context: Context): ApiService =
        ServiceGenerator.makeService(BuildConfig.DEBUG, context)

    /** provide dependency example remote */
    @Singleton
    @Provides
    fun provideExampleRemote(request: Request, apiService: ApiService): ExampleRemote =
        ExampleRemoteImpl(request, apiService)

}