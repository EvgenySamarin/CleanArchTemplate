package com.example.cleanarchtemplate.data.repository.example

import com.example.cleanarchtemplate.domain.core.Either
import com.example.cleanarchtemplate.domain.core.Failure
import com.example.cleanarchtemplate.domain.core.entity.ServerResponseEntity
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import java.io.File

/**
 * Interface implementation for interactions between "UseСase" and core repository
 *
 * @author EvgenySamarin
 * @since 20200129 v2
 */
interface ExampleRepository {
    /** @since 20200313 v1: example of remote xform request */
    fun exampleXForm(
        param1: Int?,
        param2: String?,
        param3: String?
    ): Either<Failure, ServerResponseEntity>

    /** @since 20200313 v1: example of remote get request */
    fun exampleGet(): Either<Failure, List<ExampleEntity>>

    /** @since 20200313 v1: example of multipart request */
    fun exampleMultipart(
        param1: Int?,
        param2: String?,
        param3: String?,
        param4: File?
    ): Either<Failure, ServerResponseEntity>

    /** @since 20200313 v1: example of body request */
    fun exampleBody(param1: Int, param2: String): Either<Failure, ServerResponseEntity>

    /** @since 20200213 v1: get cached entities from database */
    suspend fun getCachedExamples(): Either<Failure, List<ExampleEntity>?>
}