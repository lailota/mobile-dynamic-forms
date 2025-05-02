package com.cloudplusplus.dynamicforms.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cloudplusplus.dynamicforms.data.model.Section

@Dao
interface SectionDao {
    @Query("SELECT * FROM sections ORDER BY `index`")
    suspend fun getAll(): List<Section>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sections: List<Section>)

    @Query("DELETE FROM sections")
    suspend fun clearAll()
}
