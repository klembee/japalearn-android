package com.japalearn.mobile.ui.kanjis.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.KanjiCategory

class KanjisFragmentViewModel: ViewModel() {
    private val _categories = MutableLiveData<List<KanjiCategory>>()
    val categories: LiveData<List<KanjiCategory>> = _categories
}