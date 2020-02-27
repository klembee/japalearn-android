package com.japalearn.mobile.data.datasources

import android.util.Log
import com.japalearn.mobile.data.api.ApiController
import com.japalearn.mobile.data.api.kanji.KanjiCategoryResponse
import com.japalearn.mobile.data.api.kanji.KanjiResponse
import com.japalearn.mobile.data.repositories.DictionaryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KanjiDataSource {

    /**
     * Get the categories of kanji with their associated kanjis
     */
    fun getCategories(callback: (KanjiCategoryResponse) -> Unit){
        ApiController.kanjiApi.getCategories().enqueue(object: Callback<KanjiCategoryResponse>{
            override fun onFailure(call: Call<KanjiCategoryResponse>, t: Throwable) {
                Log.e("KanjiDataSource", "Error while loading kanji categories")
            }

            override fun onResponse(
                call: Call<KanjiCategoryResponse>,
                response: Response<KanjiCategoryResponse>
            ) {
                val body = response.body()
                if(body != null){
                    callback(body)
                }else{
                    Log.e("KanjiDataSource", "Error while loading kanji categories: ${response}")
                }
            }
        })
    }

    fun getKanjisInCategory(category: String, callback: (KanjiResponse) -> Unit){
        ApiController.kanjiApi.getKanjisInCategory(category).enqueue(object: Callback<KanjiResponse>{
            override fun onFailure(call: Call<KanjiResponse>, t: Throwable) {
                Log.e("KanjiDataSource", "Error while loading kanjis in a category")
            }

            override fun onResponse(call: Call<KanjiResponse>, response: Response<KanjiResponse>) {
                val body = response.body()
                if(body != null){
                    callback(body)
                }else{
                    Log.e("KanjiDataSource", "Error while loading kanjis in a category")
                }
            }
        })
    }

    fun getKanji(id: Int, callback: (KanjiResponse) -> Unit){
        ApiController.kanjiApi.getKanji(id).enqueue(object: Callback<KanjiResponse>{
            override fun onFailure(call: Call<KanjiResponse>, t: Throwable) {
                Log.e("KanjiDataSource", "Error while loading kanji information")
            }

            override fun onResponse(call: Call<KanjiResponse>, response: Response<KanjiResponse>) {
                val body = response.body()
                if(body != null){
                    callback(body)
                }else{
                    Log.e("KanjiDataSource", "Error while loading kanji information")
                }
            }
        })
    }
}