package com.cloudplusplus.dynamicforms.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "sections")
data class Section(
    @PrimaryKey val uuid: String,
    val title: String,
    val from: Int,
    val to: Int,
    val index: Int
)