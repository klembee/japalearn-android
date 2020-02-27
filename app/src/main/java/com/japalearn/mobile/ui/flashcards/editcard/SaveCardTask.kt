package com.japalearn.mobile.ui.flashcards.editcard

import android.os.AsyncTask
import android.util.Log
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.data.repositories.FlashCardRepository
import com.japalearn.mobile.data.repositories.LexicRepository

class SaveCardTask(
    val deck: DeckAndAllCards?,
    val frontContent: String,
    val backContent: String,
    val flashCardRepo: FlashCardRepository?,
    val lexicRepo: LexicRepository?,
    val callback: () -> Unit
): AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg p0: Unit?) {
        flashCardRepo?.let {
            it.saveCard(deck, frontContent, backContent, callback)
        }

        //Save the word to the lexic database
        lexicRepo?.let {
            it.addToLexic(listOf(
                Vocab(
                    frontContent,
                    listOf(backContent),
                    0
                )
            )){
                Log.i("SaveCardTask", "Successfully saved lexic: $it")
            }
        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        callback()
    }
}