package com.japalearn.mobile.data.repositories

import com.japalearn.mobile.data.api.dictionary.DictionaryResponse
import com.japalearn.mobile.data.datasources.DictionaryDataSource

/**
 * Repository used to talk with the dictionary API
 * @author Clement Bisaillon
 */
class DictionaryRepository(
    val cloudDataSource: DictionaryDataSource
) {

    fun search(query: String, callback: (DictionaryResponse) -> Unit){
        cloudDataSource.search(query, callback)
    }

    companion object{
        fun instantiate(): DictionaryRepository?{
            return DictionaryRepository(DictionaryDataSource())
        }
    }
}