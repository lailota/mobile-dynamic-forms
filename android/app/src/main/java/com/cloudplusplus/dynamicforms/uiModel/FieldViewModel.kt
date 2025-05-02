package com.cloudplusplus.dynamicforms.uiModel

import androidx.lifecycle.viewModelScope
import com.cloudplusplus.dynamicforms.data.model.Field
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

class FieldViewModel(
    app: Application,
    private val asset: String
) : AndroidViewModel(app) {

    private val _fields = MutableStateFlow<List<Field>>(emptyList())
    val fields: StateFlow<List<Field>> = _fields

    init {
        viewModelScope.launch {
            // Reads JSON from assets/<asset>
            val jsonText = app.assets.open(asset)
                .bufferedReader()
                .use { it.readText() }

            // Extract the "fields" array and deserialize it
            val root = Json.parseToJsonElement(jsonText).jsonObject
            val fieldsJson = root["fields"]!!
            val list: List<Field> = Json.decodeFromJsonElement(fieldsJson)

            _fields.value = list
        }
    }
}
