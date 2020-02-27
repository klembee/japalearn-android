package com.japalearn.mobile.ui.kanjis.viewKanji

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.learning.Kanji

class KanjiViewModel: ViewModel() {
    private val _kanji = MutableLiveData<Kanji>()
    val kanji: LiveData<Kanji> = _kanji

    fun setKanji(kanji: Kanji){
        _kanji.postValue(kanji)
    }
}