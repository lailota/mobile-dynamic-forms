package com.cloudplusplus.dynamicforms.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cloudplusplus.dynamicforms.data.db.AppDatabaseHolder
import com.cloudplusplus.dynamicforms.data.model.FormEntry
import kotlinx.serialization.json.Json
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntriesScreen(
    asset: String,
    entries: List<FormEntry>,
    onNewEntry: ()->Unit,
    onEntryClick: (FormEntry)->Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // 1) State to map uuid → label
    val fieldLabels by produceState(
        initialValue = emptyMap<String, String>(),
        key1 = context                                
    ) {
        val dao    = AppDatabaseHolder.getInstance(context).fieldDao()
        val fields = dao.getAll()                    
        value = fields.associate { it.uuid to it.label }
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { BasicText(asset) }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewEntry,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nova entrada")
            }
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(entries) { entry ->
                // 2) Parse to jsonData
                val dataMap: Map<String, String> = remember(entry.jsonData) {
                    runCatching {
                        Json.decodeFromString<Map<String, String>>(entry.jsonData)
                    }.getOrDefault(emptyMap())
                }

                // 3) Display each uuid→value pair as “Label:Value”
                Column(Modifier
                    .fillMaxWidth()
                    .clickable { onEntryClick(entry) }
                    .padding(16.dp)
                ) {
                    BasicText(
                        text = "Enviado: ${Date(entry.timestamp)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(8.dp))
                    dataMap.forEach { (uuid, value) ->
                        val label = fieldLabels[uuid] ?: uuid
                        BasicText(text = "$label: $value")
                    }
                }
                Divider()
            }
        }
    }
}
