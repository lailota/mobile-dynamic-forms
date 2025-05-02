package com.cloudplusplus.dynamicforms.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
      CREATE TABLE IF NOT EXISTS `form_entries` (
        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        `assetFileName` TEXT NOT NULL,
        `jsonData` TEXT NOT NULL,
        `timestamp` INTEGER NOT NULL
      )
    """.trimIndent())
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // schema changes from v2 to v3
    }
}
