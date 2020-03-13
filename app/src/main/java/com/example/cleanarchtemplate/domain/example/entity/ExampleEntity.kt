package com.example.cleanarchtemplate.domain.example.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Example DTO to save it into database
 *
 * @author EvgenySamarin
 * @since 20200129 v1
 */
@Keep
@Serializable
@Entity(tableName = "example")
data class ExampleEntity(
    @PrimaryKey val id: Int,
    val payload: String,
    val optional: String = "is default", //is optional field
    @Transient
    val isTransient: Boolean = false
)