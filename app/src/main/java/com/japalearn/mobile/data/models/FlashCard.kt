package com.japalearn.mobile.data.models

import androidx.room.*
import java.io.Serializable

/**
 * Represents a flash card
 */

@Entity(tableName = "cards",
    foreignKeys = [ForeignKey(entity = FlashCardDeck::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("deck_id"),
    onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["deck_id"], unique = false)]
)
data class FlashCard (
    @ColumnInfo(name = "deck_id")
    val deckId: Int,

    val front: String,

    val back: String
): Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}