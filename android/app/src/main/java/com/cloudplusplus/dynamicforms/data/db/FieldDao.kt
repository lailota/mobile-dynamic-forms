package com.cloudplusplus.dynamicforms.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cloudplusplus.dynamicforms.data.model.Field

@Dao
interface FieldDao {
    @Query("SELECT * FROM fields ORDER BY rowid")
    suspend fun getAll(): List<Field>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fields: List<Field>)

    @Query("DELETE FROM fields")
    suspend fun clearAll()
}