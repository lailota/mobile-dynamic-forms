package com.cloudplusplus.dynamicforms.data.db

import android.content.Context
import com.cloudplusplus.dynamicforms.data.model.Field
import com.cloudplusplus.dynamicforms.data.model.Section
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class DataSeeder(
    private val context: Context,
    private val assetFileName: String,
    private val fieldDao: FieldDao,
    private val sectionDao: SectionDao
) {
    suspend fun seedIfNeeded() = withContext(Dispatchers.IO) {
        // we only sow in the first execution
        if (fieldDao.getAll().isEmpty()) {
            // 1) reads the complete JSON as a string
            val jsonText = context.assets
                .open(assetFileName)
                .bufferedReader()
                .use { it.readText() }

            // 2) parse to JsonObject
            val root = Json.parseToJsonElement(jsonText).jsonObject

            // 3) extracts and deserializes "fields"
            val fieldsJson = root["fields"]!!.jsonArray
            val fields: List<Field> = Json.decodeFromString(fieldsJson.toString())

            // 4) extracts and deserializes "sections"
            val sectionsJson = root["sections"]!!.jsonArray
            val sections: List<Section> = Json.decodeFromString(sectionsJson.toString())

            // 5)record in the bank
            fieldDao.insertAll(fields)
            sectionDao.insertAll(sections)
        }
    }
}


