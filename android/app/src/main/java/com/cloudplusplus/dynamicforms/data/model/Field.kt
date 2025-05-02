package com.cloudplusplus.dynamicforms.data.model

import androidx.room.*
import com.cloudplusplus.dynamicforms.data.db.Converters
import kotlinx.serialization.*

@Serializable
data class Option(
    val label: String,
    val value: String
)

@Entity(tableName = "fields")
@Serializable
data class Field(
    @PrimaryKey val uuid: String,
    val type: String,
    val label: String,
    val name: String,
    val required: Boolean = false,

    // for dropdown (and to treat other types as text)
    @TypeConverters(Converters::class)
    val options: List<Option>? = null
)
