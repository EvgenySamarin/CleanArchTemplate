package com.example.cleanarchtemplate.data.remote

import android.content.Context
import androidx.annotation.Keep
import com.example.cleanarchtemplate.BuildConfig
import com.example.cleanarchtemplate.data.remote.core.MockInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Singleton factory to create retrofit api instance
 *
 * @author EvgenySamarin
 * @since 20190905 v3
 */
@Keep
object ServiceGenerator {
    /**
     * Предоставляет сервис для доступа к REST API
     *
     * @param context контекст приложения, необходим для установки http кеширования
     * @param isDebug в дебаг режиме происходит логирование запросов, в продакшн логирования нет
     */
    @UnstableDefault
    fun makeService(isDebug: Boolean, context: Context): ApiService {
        return makeRetrofit(
            makeOkHttpClient(
                makeLoggingInterceptor(isDebug), context
            )
        ).create(ApiService::class.java)
    }

    /** создаём экземпляр базового интерфейса ApiService */
    @UnstableDefault
    private fun makeRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    /** создание класса занимающегося логированием REST запросов */
    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    /** создаём клиента с помощью предоставленного перехватчика логов */
    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, context: Context
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val builder = OkHttpClient.Builder()
            .cache(myCache)  //параметры кеша
            .addInterceptor(httpLoggingInterceptor).apply {
                @Suppress("ConstantConditionIf")
                if (BuildConfig.MOCKS) addInterceptor(MockInterceptor())
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)

        return builder.build()
    }

    private fun makeAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, context: Context
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .cache(myCache)  //параметры кеша
            .addInterceptor(httpLoggingInterceptor).apply {
                @Suppress("ConstantConditionIf")
                if (BuildConfig.MOCKS) addInterceptor(MockInterceptor())
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
}