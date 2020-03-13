package com.example.cleanarchtemplate.data.repository.example

import com.example.cleanarchtemplate.data.cache.database.AppRoomDatabase
import com.example.cleanarchtemplate.data.remote.example.ExampleRemote
import com.example.cleanarchtemplate.domain.core.Either
import com.example.cleanarchtemplate.domain.core.Failure
import com.example.cleanarchtemplate.domain.core.entity.ServerResponseEntity
import com.example.cleanarchtemplate.domain.core.onNext
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * Implementation of the appropriate interface
 *
 * @author EvgenySamarin
 * @since 20200129 v2
 */
class ExampleRepositoryImpl @Inject constructor(
    private val remote: ExampleRemote,
    private val database: AppRoomDatabase
) : ExampleRepository {

    override fun exampleXForm(
        param1: Int?,
        param2: String?,
        param3: String?
    ): Either<Failure, ServerResponseEntity> = remote.exampleXForm(param1, param2, param3)

    override fun exampleGet(): Either<Failure, List<ExampleEntity>> =
        remote.exampleGet().onNext {
            CoroutineScope(Dispatchers.IO).launch {
                cachedExamples(it)
            }
        }

    override fun exampleMultipart(
        param1: Int?,
        param2: String?,
        param3: String?,
        param4: File?
    ): Either<Failure, ServerResponseEntity> =
        remote.exampleMultipart(param1, param2, param3, param4)

    override fun exampleBody(param1: Int, param2: String): Either<Failure, ServerResponseEntity> =
        remote.exampleBody(param1, param2)

    override suspend fun getCachedExamples(): Either<Failure, List<ExampleEntity>?> =
        Either.Right(database.exampleDAO().getAllItem())


    private suspend fun cachedExamples(list: List<ExampleEntity>) {
        database.exampleDAO().insertList(list)
    }
}