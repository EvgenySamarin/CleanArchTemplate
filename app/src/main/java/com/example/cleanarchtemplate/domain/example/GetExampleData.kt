package com.example.cleanarchtemplate.domain.example


import com.example.cleanarchtemplate.data.repository.example.ExampleRepository
import com.example.cleanarchtemplate.domain.core.Either
import com.example.cleanarchtemplate.domain.core.Failure
import com.example.cleanarchtemplate.domain.core.None
import com.example.cleanarchtemplate.domain.core.UseCase
import com.example.cleanarchtemplate.domain.example.entity.ExampleEntity
import javax.inject.Inject

/**
 * Get example data UseCase
 *
 * @author EvgenySamarin
 * @since 20200129 v1
 */
class GetExampleData @Inject constructor(private val repository: ExampleRepository) :
    UseCase<List<ExampleEntity>?, None>() {

    override suspend fun run(params: None): Either<Failure, List<ExampleEntity>?> =
        repository.exampleGet()
}