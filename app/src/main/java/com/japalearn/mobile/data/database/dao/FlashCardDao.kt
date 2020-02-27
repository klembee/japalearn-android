package com.japalearn.mobile.data.database.dao

import androidx.room.*
import com.japalearn.mobile.data.models.FlashCard

@Dao
interface FlashCardDao {

    @Insert
    fun insert(card: FlashCard)

    @Update
    fun update(card: FlashCard)

    @Delete
    fun delete(card: FlashCard)

}