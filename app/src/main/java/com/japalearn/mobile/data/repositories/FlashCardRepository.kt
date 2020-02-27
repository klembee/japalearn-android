package com.japalearn.mobile.data.repositories

import android.content.Context
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.models.FlashCard
import com.magestionplus.android.data.database.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Used to get and update flash cards
 * @author Clement Bisaillon
 */
class FlashCardRepository(
    val localDataSource: AppDatabase
) {

    /**
     * Saves the card to the database
     */
    fun saveCard(deck: DeckAndAllCards?, front: String, back: String, callback: () -> Unit){
        val card = FlashCard(deck?.deck?.id ?: -1, front, back)
        GlobalScope.launch {
            localDataSource.flashCardDao().insert(card)
            callback()
        }
    }

    companion object{
        fun instantiate(context: Context?): FlashCardRepository?{
            context?.let {
                return FlashCardRepository(AppDatabase.invoke(it))
            }
            return null
        }
    }
}