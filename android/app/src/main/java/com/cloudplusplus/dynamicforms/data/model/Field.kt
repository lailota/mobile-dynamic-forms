package com.cloudplusplus.dynamicforms.data.model

import androidx.room.*
import kotlinx.serialization.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

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

    // para dropdown (e para tratar outros tipos como texto)
    @TypeConverters(Converters::class)
    val options: List<Option>? = null
)