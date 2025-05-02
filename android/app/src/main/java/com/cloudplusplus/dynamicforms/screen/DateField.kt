package com.cloudplusplus.dynamicforms.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.cloudplusplus.dynamicforms.data.model.Field

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateField(
    field: Field,
    values: SnapshotStateMap<String, String>
) {
    var raw by rememberSaveable { mutableStateOf("") } // only digits
    OutlinedTextField(
        value = raw,
        onValueChange = { new ->
            // we keep only digits and limit it to 8 (ddMMyyyy)
            raw = new.filter { it.isDigit() }.take(8)
            values[field.uuid] = raw
        },
        label = { BasicText(field.label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = DateVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // We only accept up to 8 digits (ddMMyyyy)
        val digits = text.text.filter { it.isDigit() }.take(8)
        // We insert the bars
        val formatted = buildString {
            digits.forEachIndexed { i, c ->
                append(c)
                if (i == 1 || i == 3) append('/')
            }
        }

        val offsetTranslator = object : OffsetMapping {
            // from original (without bars) to transformed (with bars)
            override fun originalToTransformed(offset: Int): Int = when {
                offset <= 1 -> offset
                offset <= 3 -> offset + 1
                offset <= 8 -> offset + 2
                else        -> formatted.length
            }

            // from transformed (with bars) to original (without bars)
            override fun transformedToOriginal(offset: Int): Int = when {
                offset <= 2 -> offset
                offset <= 5 -> offset - 1
                offset <= 10-> offset - 2
                else        -> digits.length
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetTranslator)
    }
}

