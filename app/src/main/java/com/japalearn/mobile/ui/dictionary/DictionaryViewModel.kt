package com.japalearn.mobile.ui.dictionary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.DictionaryEntry

class DictionaryViewModel : ViewModel() {

    var adapter: DictionaryAdapter? = null
        set(value) {
            if(field == null) {
                field = value
            }
        }
    val selectedEntries = HashSet<DictionaryEntry>()

    private val _text = MutableLiveData<String>().apply {
        value = "Dictionary"
    }
    val text: LiveData<String> = _text

    fun setEntries(words: List<DictionaryEntry>){
        adapter?.changeEntries(words)
    }

    fun setSelected(word: DictionaryEntry){
        if(!selectedEntries.contains(word)){
            selectedEntries.add(word)
            adapter?.setSelectedEntries(selectedEntries)
        }else{
            //Remove the word from the lexic
            selectedEntries.remove(word)
            adapter?.setSelectedEntries(selectedEntries)
        }
    }
}