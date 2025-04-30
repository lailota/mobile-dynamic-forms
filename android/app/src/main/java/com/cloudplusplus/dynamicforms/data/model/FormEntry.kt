package com.cloudplusplus.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form_entries")
data class FormEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val assetFileName: String,
    val jsonData: String,
    val timestamp: Long
)
