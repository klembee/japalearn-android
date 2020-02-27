package com.japalearn.mobile.ui.review

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.data.repositories.LexicRepository

class ReviewViewModel: ViewModel() {
    private var lexicRepository: LexicRepository? = null
    private val _lexics = MutableLiveData<List<Vocab>>()
    val lexics: LiveData<List<Vocab>> = _lexics
    private val _toReview = MutableLiveData<List<Vocab>>()
    val toReview:LiveData<List<Vocab>> = _toReview

    fun loadData(context: Context?){
        lexicRepository = LexicRepository.instantiate(context)
        lexicRepository?.getAllLexics {
            _lexics.postValue(it)

            val listOfLexicToStudy = ArrayList<Vocab>()
            it.forEach {
                if(it.needToReview()){
                    listOfLexicToStudy.add(it)
                }
            }
            _toReview.postValue(listOfLexicToStudy.toList())
        }

    }
}