package com.japalearn.mobile.ui.flashcards.editcard

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.repositories.FlashCardRepository
import com.japalearn.mobile.data.repositories.LexicRepository

class EditFlashCardViewModel: ViewModel(), AdapterView.OnItemSelectedListener {
    private var decks = emptyList<DeckAndAllCards>()

    private val _selectedDeck = MutableLiveData<DeckAndAllCards>()
    val selectedDeck: LiveData<DeckAndAllCards> = _selectedDeck

    private val _frontContent = MutableLiveData<String>()
    val frontContent: LiveData<String> = _frontContent

    private val _backContent = MutableLiveData<String>()
    val backContent: LiveData<String> = _backContent

    fun setDecks(list: List<DeckAndAllCards>){
        decks = list
    }

    /**
     * Sets the content of the front card
     */
    fun setFrontContent(string: String){
        _frontContent.postValue(string)
    }

    /**
     * Sets the content of the back card
     */
    fun setBackContent(string: String){
        _backContent.postValue(string)
    }

    fun save(flashCardRepo: FlashCardRepository?, lexicRepo: LexicRepository?, callback: () -> Unit){
        SaveCardTask(selectedDeck.value, frontContent.value ?: "", backContent.value ?: "", flashCardRepo, lexicRepo){
            callback()
        }.execute()
    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {
        _selectedDeck.postValue(null)
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        if(decks.isEmpty()){
            _selectedDeck.postValue(null)
        }else{
            _selectedDeck.postValue(decks[position])
        }
    }
}