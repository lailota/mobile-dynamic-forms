package com.cloudplusplus.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "sections")
@Serializable
data class Section(
    @PrimaryKey val uuid: String,
    val title: String,
    val from: Int,
    val to: Int,
    val index: Int
)