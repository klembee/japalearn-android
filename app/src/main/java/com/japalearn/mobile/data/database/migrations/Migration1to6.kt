package com.japalearn.mobile.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1to6: Migration(1, 6) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE lexic ADD `onReadings` varchar(255) DEFAULT(''), `d`")
    }

}