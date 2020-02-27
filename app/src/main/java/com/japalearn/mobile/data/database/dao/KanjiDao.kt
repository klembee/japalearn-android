package com.japalearn.mobile.data.database.dao

import androidx.room.*
import com.japalearn.mobile.data.models.learning.Kanji

@Dao
interface KanjiDao {
    @Query("SELECT * FROM kanji")
    fun get(): List<Kanji>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kanji: Kanji)

    @Update
    fun update(kanji: Kanji)

    @Delete
    fun delete(kanji: Kanji)
}