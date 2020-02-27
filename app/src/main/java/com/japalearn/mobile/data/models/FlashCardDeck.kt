package com.japalearn.mobile.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "decks")
data class FlashCardDeck (
    val title: String
): Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}