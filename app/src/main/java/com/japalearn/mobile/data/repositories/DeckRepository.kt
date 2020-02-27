package com.japalearn.mobile.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.models.FlashCardDeck
import com.magestionplus.android.data.database.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Used to access the decks
 * @author Clement Bisaillon
 */
class DeckRepository(
    val localDataSource: AppDatabase
) {

    /**
     * Get the decks from the database
     */
    fun getDecks(): LiveData<List<DeckAndAllCards>>{
        val list =  MutableLiveData<List<DeckAndAllCards>>()

        GlobalScope.launch {
            list.postValue(localDataSource.deckDao().getDecks())
        }

        return list
    }

    /**
     * Save the deck to the database
     */
    fun saveDeck(deckId: Int? = null, title: String, callback: () -> Unit){
        val deck = FlashCardDeck(title)
        GlobalScope.launch {
            if(deckId == null){
                //The user is creating a new deck
                localDataSource.deckDao().insert(deck)
            }else{
                //The user is updating an existing deck
                deck.id = deckId
                localDataSource.deckDao().update(deck)
            }

            callback()
        }
    }

    companion object{
        fun instantiate(context: Context?): DeckRepository?{
            context?.let {
                return DeckRepository(AppDatabase.invoke(it))
            }
            return null
        }
    }
}