package com.example.cleanarchtemplate.data.remote.example.body

import androidx.annotation.Keep

/**
 * Body params for REST API "addChildMeasure" request
 *
 * @author EvgenySamarin
 * @since 20200304 v1
 */
@Keep
data class ExampleBody(
    val id: Int,
    val payload: String
)