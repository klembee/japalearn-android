package com.japalearn.mobile.data.datasources

import android.util.Log
import com.google.gson.Gson
import com.japalearn.mobile.data.api.ApiController
import com.japalearn.mobile.data.api.ModelResponse
import com.japalearn.mobile.data.models.learning.Vocab

class LexicDataSource {
    fun sync(vocabs: List<Vocab>, token: String, lastSync: Long): ModelResponse<Vocab>?{
        Log.i("ASDF", "token: $token")
        vocabs.forEach {

            Log.i("ASDF", "lexic: ${it}")
        }

        val result = ApiController.lexicApi.sync("Bearer $token", Gson().toJson(vocabs), lastSync).execute()
        Log.i("ASDF", "$result")
        return result.body()
    }
}