package com.cloudplusplus.dynamicforms.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cloudplusplus.dynamicforms.data.model.Field
import com.cloudplusplus.dynamicforms.data.model.FormEntry
import com.cloudplusplus.dynamicforms.data.model.Section


@Database(
    entities = [Field::class, Section::class, FormEntry::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fieldDao(): FieldDao
    abstract fun sectionDao(): SectionDao
    abstract fun formEntryDao(): FormEntryDao
}