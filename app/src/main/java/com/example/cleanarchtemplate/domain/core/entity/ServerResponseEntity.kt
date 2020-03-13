package com.example.cleanarchtemplate.domain.core.entity

import androidx.annotation.Keep
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Base server response data
 *
 * @param message server message
 *
 * @author EvgenySamarin
 * @since 20190916 v2
 */
@Keep
@Serializable
data class ServerResponseEntity(
    val message: String
)

