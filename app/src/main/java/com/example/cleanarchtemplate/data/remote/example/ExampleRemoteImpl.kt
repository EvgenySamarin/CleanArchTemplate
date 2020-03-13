package com.example.cleanarchtemplate.data.remote.example

import com.example.cleanarchtemplate.data.remote.ApiService
import com.example.cleanarchtemplate.data.remote.core.Request
import com.example.cleanarchtemplate.data.remote.example.body.ExampleBody
import com.example.cleanarchtemplate.domain.core.Either
import com.example.cleanarchtemplate.domain.core.Failure
import com.example.cleanarchtemplate.domain.core.entity.ServerResponseEntity
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.LinkedHashMap
import javax.inject.Inject

/**
 * Dialogs remote interface implementation
 *
 * @author EvgenySamarin
 * @since 20191225 v1
 */
class ExampleRemoteImpl @Inject constructor(
    private val request: Request,
    private val service: ApiService
) : ExampleRemote {

    override fun exampleMultipart(
        param1: Int?,
        param2: String?,
        param3: String?,
        param4: File?
    ): Either<Failure, ServerResponseEntity> =
        request.make(
            service.exampleMultipart(
                createMultipartMap(param1, param2, param3),
                createMultipartFromFile(param4)
            )
        ) {
            it
        }

    override fun exampleXForm(
        param1: Int?,
        param2: String?,
        param3: String?
    ): Either<Failure, ServerResponseEntity> = request.make(
        service.exampleXForm(createXFormMap(param1, param2, param3))
    ) { it }

    override fun exampleGet(): Either<Failure, List<ExampleEntity>> = request.make(
        service.exampleGet()
    ) {
        it
    }

    override fun exampleBody(
        param1: Int, param2: String
    ): Either<Failure, ServerResponseEntity> = request.make(
        service.exampleBody(ExampleBody(param1, param2))
    ) {
        it
    }

    private fun createMultipartFromFile(param4: File?): MultipartBody.Part? = param4?.let {
        /** TODO [20200217] h~=3: добавить работу с файлом */
        val requestFile = it.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData(ApiService.PARAM_EXAMPLE, it.name, requestFile)
        body
    }

    private fun createMultipartMap(
        param1: Int?,
        param2: String?,
        param3: String?
    ): LinkedHashMap<String, RequestBody> {
        val map: LinkedHashMap<String, RequestBody> = LinkedHashMap()

        var rb: RequestBody
        param1?.let {
            rb = it.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            map[ApiService.PARAM_ID_EXAMPLE] = rb
        }
        param2?.let {
            rb = it.toRequestBody("text/plain".toMediaTypeOrNull())
            map[ApiService.PARAM_EXAMPLE] = rb
        }

        param3?.let {
            rb = it.toRequestBody("text/plain".toMediaTypeOrNull())
            map[ApiService.PARAM_EXAMPLE] = rb
        }
        return map
    }

    private fun createXFormMap(
        param1: Int?,
        param2: String?,
        param3: String?
    ): Map<String, String> {
        val map = HashMap<String, String>()
        param1?.let { map[ApiService.PARAM_EXAMPLE] = it.toString() }
        param2?.let { map[ApiService.PARAM_EXAMPLE] = it }
        param3?.let { map[ApiService.PARAM_EXAMPLE] = it }
        return map
    }
}