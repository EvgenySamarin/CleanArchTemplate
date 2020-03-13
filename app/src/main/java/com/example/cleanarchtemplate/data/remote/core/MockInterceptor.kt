package com.example.cleanarchtemplate.data.remote.core

import com.example.cleanarchtemplate.BuildConfig
import com.example.cleanarchtemplate.data.remote.ApiService
import com.example.cleanarchtemplate.data.remote.core.mock.getMockGetExample_200
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Retrofit interceptor which responsible to mock http response. Use it if you don`t have a
 * back-end API
 *
 * @author EvgenySamarin
 * @since 20190927 v8
 */
class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith(ApiService.GET_EXAMPLE) -> getMockGetExample_200
                else -> null
            }

            return if (responseString != null) {
                chain.proceed(chain.request())
                    .newBuilder()
                    .code(200)
                    .protocol(Protocol.HTTP_2)
                    .message(responseString)
                    .body(
                        responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    .addHeader("content-type", "application/json")
                    .build()
            } else {
                chain.proceed(chain.request())
            }
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and bound to be used only with DEBUG mode"
            )
        }
    }
}