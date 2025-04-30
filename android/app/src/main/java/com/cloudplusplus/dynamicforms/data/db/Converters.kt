package com.cloudplusplus.dynamicforms.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.cloudplusplus.dynamicforms.data.model.Option

object Converters {
    @TypeConverter
    fun fromOptions(value: List<Option>?): String? =
        value?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toOptions(value: String?): List<Option>? =
        value?.let { Json.decodeFromString(it) }
}

