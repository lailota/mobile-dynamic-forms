package com.cloudplusplus.dynamicforms.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.cloudplusplus.dynamicforms.data.db.AppDatabaseHolder
import com.cloudplusplus.dynamicforms.data.model.Field
import com.cloudplusplus.dynamicforms.data.model.FormEntry
import com.cloudplusplus.dynamicforms.data.model.Section
import com.cloudplusplus.dynamicforms.screen.FieldItem
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicFormScreen(
    assetFileName: String,
    fields: List<Field>,
    sections: List<Section>,
    values: SnapshotStateMap<String, String>,
    modifier: Modifier = Modifier,
    onFormSubmitted: () -> Unit
) {
    // Context and CoroutineScope for I/O operations
    val context = LocalContext.current
    val scope   = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
    ) {
        sections.forEach { section ->
            // 2) header of each section
            item {
                BasicText(
                    text = HtmlCompat
                        .fromHtml(section.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        .toString(),
                    style    = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            // 3) fields in that section
            val slice = fields.subList(section.from, section.to + 1)
            items(slice, key = { it.uuid }) { field ->
                FieldItem(field = field, values = values)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // 4) submit button
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        // serializes to JSON and saves to DB
                        val json = Json.encodeToString(values.toMap())
                        val db   = AppDatabaseHolder.getInstance(context)
                        db.formEntryDao().insert(
                            FormEntry(
                                assetFileName = assetFileName,
                                jsonData      = json,
                                timestamp     = System.currentTimeMillis()
                            )
                        )
                        // notifies who called
                        onFormSubmitted()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                BasicText("Enviar")
            }
        }
    }
}
