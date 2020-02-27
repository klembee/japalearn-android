package com.japalearn.mobile.ui.flashcards.play

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.data.repositories.LexicRepository
import kotlin.random.Random

class FlashCardPlayViewModel: ViewModel() {
    private var lexicRepository: LexicRepository? = null

    private val _cards = MutableLiveData<List<Vocab>>()
    val cards: LiveData<List<Vocab>> = _cards

    private val _currentCard = MutableLiveData<Vocab>()
    val currentCard: LiveData<Vocab> = _currentCard

    private var currentCardIndex = 0
    val gotRight = HashSet<Vocab>()
    val gotWrong = HashSet<Vocab>()

    fun init(context: Context){
        lexicRepository = LexicRepository.instantiate(context)
    }

    /**
     * Sets the lexics to practice
     */
    fun setDeck(deck: List<Vocab>){
        val shuffled = deck.shuffled()
        _cards.postValue(shuffled)

        //Show the first card
        _currentCard.postValue(shuffled[currentCardIndex++])
    }

    /**
     * Goes to the next card
     */
    fun nextCard(gotItRight: Boolean){
        val lexic = _currentCard.value

        //Updates the level of the lexic
        lexic?.let {
            if(gotItRight){
                gotRight.add(lexic)
            }else{
                this.replaceCard(lexic)
                gotWrong.add(lexic)
            }
        }

        cards.value?.let {
            if(currentCardIndex < it.size) {
                _currentCard.postValue(it[currentCardIndex++])
            }else{
                _currentCard.postValue(null)
            }
        }
    }

    private fun replaceCard(vocab: Vocab){
        //Add the same card in 3, 4 or 5 card next
        var nextIndex = Random.nextInt(3, 6);
        val editCardsList = ArrayList<Vocab>()
        _cards.value?.let {
            editCardsList.addAll(it)
        }

        if(currentCardIndex + nextIndex > editCardsList.size){
            nextIndex = editCardsList.size - currentCardIndex
        }

        Log.i("ASDF", nextIndex.toString())

        editCardsList.add(currentCardIndex + nextIndex, vocab)
        _cards.postValue(editCardsList)
    }

    fun saveProgress(){
        gotRight.forEach {
            lexicRepository?.updateLevel(it, true)
        }
        gotWrong.forEach {
            lexicRepository?.updateLevel(it, false)
        }
    }
}