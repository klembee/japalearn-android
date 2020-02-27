package com.japalearn.mobile.data.models

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class DeckAndAllCards (
    @Embedded
    val deck: FlashCardDeck,

    @Relation(parentColumn = "id",
        entityColumn = "deck_id")
    val cards: List<FlashCard>
): Serializable