package com.japalearn.mobile.data.database.dao

import androidx.room.*
import com.japalearn.mobile.data.models.learning.Vocab
import java.util.*

@Dao
interface VocabDao {
    @Query("SELECT * FROM lexic WHERE word=:word")
    fun retrieve(word: String): Vocab

    @Query("SELECT * FROM lexic where deleted=0")
    fun retrieveAll(): List<Vocab>

    @Query("SELECT * FROM lexic WHERE synced=0 AND deleted=0")
    fun getUnsynced(): List<Vocab>

    @Query("UPDATE lexic SET synced=1 WHERE id=:lexicId")
    fun setSynced(lexicId: Int)

    @Query("UPDATE lexic SET level = level + 1, synced=0 WHERE id=:lexicId")
    fun increaseLevel(lexicId: Int)

    @Query("UPDATE lexic SET level = level - 1, synced=0 WHERE id=:lexicId")
    fun decreaseLevel(lexicId: Int)

    @Query("UPDATE lexic SET lastTimeStudied=:date, synced=0 WHERE id=:lexicId")
    fun setStudiedDate(lexicId: Int, date: Date = Date())

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: Vocab)

    @Update
    fun update(card: Vocab)

    @Delete
    fun delete(card: Vocab)
}