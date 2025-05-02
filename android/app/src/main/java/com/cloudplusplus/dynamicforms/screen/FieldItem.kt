package com.cloudplusplus.dynamicforms.screen

import DropdownField
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.cloudplusplus.dynamicforms.data.model.Field

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FieldItem(
    field: Field,
    values: SnapshotStateMap<String, String>
) {
    when (field.type) {
        "description" -> {
            BasicText(
                text = HtmlCompat
                    .fromHtml(field.label, HtmlCompat.FROM_HTML_MODE_LEGACY)
                    .toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        "text", "number" -> {
            OutlinedTextField(
                value = values[field.uuid].orEmpty(),
                onValueChange = { values[field.uuid] = it },
                label = { BasicText(text = field.label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                keyboardOptions = if (field.type == "number")
                    KeyboardOptions(keyboardType = KeyboardType.Number)
                else
                    KeyboardOptions.Default,
                interactionSource = remember { MutableInteractionSource() }
            )
        }
        "dropdown" -> {
            DropdownField(
                field = field,
                values = values
            )
        }
        "date" -> {
            DateField(
                field = field,
                values = values
            )
        }
        else -> {
            // Any other type treated as plain text
            OutlinedTextField(
                value = values[field.uuid].orEmpty(),
                onValueChange = { values[field.uuid] = it },
                label = { BasicText(text = field.label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                interactionSource = remember { MutableInteractionSource() }
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}
