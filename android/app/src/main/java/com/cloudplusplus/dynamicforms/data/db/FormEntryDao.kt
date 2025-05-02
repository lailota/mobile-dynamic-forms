package com.cloudplusplus.dynamicforms.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cloudplusplus.dynamicforms.data.model.FormEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface FormEntryDao {
    @Query("SELECT * FROM form_entries WHERE assetFileName = :asset ORDER BY timestamp DESC")
    fun entriesFor(asset: String): Flow<List<FormEntry>>

    @Insert
    suspend fun insert(entry: FormEntry)
}
