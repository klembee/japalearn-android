package com.japalearn.mobile.data.database.dao

import androidx.room.*
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.models.FlashCardDeck

@Dao
interface DeckDao {
    @Insert
    fun insert(deck: FlashCardDeck)

    @Update
    fun update(deck: FlashCardDeck)

    @Delete
    fun delete(deck: FlashCardDeck)

    @Query("SELECT * FROM decks")
    fun getDecks(): List<DeckAndAllCards>
}