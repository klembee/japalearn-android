package com.japalearn.mobile.ui.review.lexic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.data.models.learning.Vocab

class LexicListViewModel: ViewModel() {
    var listAdapter: LexicListAdapter? = null
        private set

    private val _lexics = MutableLiveData<List<Vocab>>()
    val lexics: LiveData<List<Vocab>> = _lexics

    fun setLexics(context: Context, vocabs: List<Vocab>?){
        vocabs?.let {
            _lexics.postValue(it)
            if(listAdapter == null){
                listAdapter = LexicListAdapter(context, it)
            }else{
                listAdapter!!.changeLexic(it)
            }
        }
    }

}