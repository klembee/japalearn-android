package com.japalearn.mobile.ui.flashcards.editdeck

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.repositories.DeckRepository

class EditDeckViewModel: ViewModel() {
    private var deck: DeckAndAllCards? = null

    private val _title = MutableLiveData<String>()
    val title:LiveData<String> = _title

    fun setDeck(deck: DeckAndAllCards){
        this.deck = deck
    }

    fun setTitle(title: String){
        _title.postValue(title)
    }

    fun save(deckRepository: DeckRepository?, callback: () -> Unit){
        deckRepository?.saveDeck(deck?.deck?.id, title.value ?: "", callback)
    }
}