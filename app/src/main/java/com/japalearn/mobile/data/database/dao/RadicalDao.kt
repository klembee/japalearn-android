package com.japalearn.mobile.data.database.dao

import androidx.room.*
import com.japalearn.mobile.data.models.learning.Radical

@Dao
interface RadicalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(radical: Radical)

    @Update
    fun update(radical: Radical)

    @Delete
    fun delete(radical: Radical)
}