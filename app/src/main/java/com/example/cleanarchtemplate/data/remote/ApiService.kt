package com.example.cleanarchtemplate.data.remote

import com.example.cleanarchtemplate.data.remote.example.body.ExampleBody
import com.example.cleanarchtemplate.domain.core.entity.ServerResponseEntity
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * REST API retrofit interface. For request should use raw body parameters (if possible)
 *
 * @author EvgenySamarin
 * @since 20200313 v1
 */
interface ApiService {
    /**
     * Contains service endpoint and request parameters, where
     *
     * Endpoints must have following prefix:
     * - POST - to create something
     * - PUT - to update something
     * - GET - to get something
     * - DELETE - to delete something
     *
     * Parameters which would be used into request by different ways. All parameters must have
     * prefix __PARAM__
     *
     * @since 20200313 v1
     */
    @Suppress("KDocMissingDocumentation")
    companion object {
        const val POST_EXAMPLE = "endpoint/post"

        const val PUT_EXAMPLE = "endpoint/put"

        const val DELETE_EXAMPLE = "endpoint/delete"

        const val GET_EXAMPLE = "endpoint/get"

        const val PARAM_ID_EXAMPLE = "id"
        const val PARAM_EXAMPLE = "example"
    }

    /** @since 20191007 v example of using xFormData. */
    @FormUrlEncoded
    @POST(POST_EXAMPLE)
    fun exampleXForm(@FieldMap params: Map<String, String>): Call<ServerResponseEntity>

    /** @since 20200313 v1: example of get request */
    @GET(GET_EXAMPLE)
    fun exampleGet(): Call<List<ExampleEntity>>

    /** @since 20200313 v1: example of multipart request */
    @Multipart
    @POST(POST_EXAMPLE)
    fun exampleMultipart(
        @PartMap params: LinkedHashMap<String, RequestBody>,
        @Part photo: MultipartBody.Part?
    ): Call<ServerResponseEntity>


    /** @since 20200313 v1: exanple with body */
    @PUT(PUT_EXAMPLE)
    fun exampleBody(@Body body: ExampleBody): Call<ServerResponseEntity>
}