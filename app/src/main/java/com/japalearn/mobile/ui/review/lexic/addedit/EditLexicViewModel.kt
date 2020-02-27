package com.japalearn.mobile.ui.review.lexic.addedit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.data.repositories.LexicRepository
import com.japalearn.mobile.ui.dictionary.DictionaryFragment

class EditLexicViewModel: ViewModel() {

    var editCustomLexicFragment: AddEditCustomLexicFragment = AddEditCustomLexicFragment()
    var dictionaryFragment: DictionaryFragment = DictionaryFragment(DictionaryFragment.MODE_CHOOSE)
    var currentTab = -1

    private val _word = MutableLiveData<String>()
    val word: LiveData<String> = _word

    private val _meaning = MutableLiveData<String>()
    val meaning: LiveData<String> = _meaning

    fun setLexic(vocab: Vocab?){
        vocab?.let {
            _word.postValue(it.word)
            _meaning.postValue(it.meanings[0])
        }
    }

    fun save(repository: LexicRepository?, callback: () -> Unit){

        if(currentTab == TAB_FROM_DICTIONARY){
            val entriesToAdd = dictionaryFragment.getSelectedEntries()
            val entriesAsLexic = ArrayList<Vocab>()
            entriesToAdd.forEach {
                //todo: Audio file
                var word = ""
                if(it.japaneseRepresentations.isEmpty()){
                    word = it.kanaRepresentations[0].representation
                }else{
                    word = it.japaneseRepresentations[0].representation
                }

                val meanings = it.getMeaningsList()
                var kanaRepresentations = it.getKanaRepresentationsList()

                entriesAsLexic.add(
                    Vocab(
                        word,
                        meanings,
                        0,
                        null,
                        kanaRepresentations
                    )
                )
            }
            repository?.addToLexic(entriesAsLexic){
                if(!it) Log.e("EditLexicViewModel", "Error while adding the lexics")
                callback()
            }

        }else if(currentTab == TAB_CUSTOM){
            repository?.addToLexic(listOf(
                Vocab(
                    _word.value.toString(),
                    listOf(_meaning.value.toString()),
                    0
                )
            )){
                if(!it) Log.e("EditLexicViewModel", "Error while adding the lexic")
                callback()
            }
        }else{
            callback()
        }


    }

    companion object {
        val TAB_FROM_DICTIONARY = 0
        val TAB_CUSTOM = 1
    }
}