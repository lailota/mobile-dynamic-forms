package com.cloudplusplus.dynamicforms.data.db

import androidx.room.TypeConverter
import com.cloudplusplus.dynamicforms.data.model.Option
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromOptionsList(list: List<Option>?): String? =
        list?.let { json.encodeToString(it) }

    @TypeConverter
    fun toOptionsList(data: String?): List<Option>? =
        data?.let { json.decodeFromString(it) }
}