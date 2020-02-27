package com.japalearn.mobile.data.datasources

import android.util.Log
import com.japalearn.mobile.data.api.ApiController
import com.japalearn.mobile.data.api.dictionary.DictionaryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * API datasource of the dictionary
 * @author Clement Bisaillon
 */
class DictionaryDataSource {
    fun search(query: String, callback: (DictionaryResponse) -> Unit){
        ApiController.dictionaryApi.search(query).enqueue(object: Callback<DictionaryResponse>{
            override fun onFailure(call: Call<DictionaryResponse>, t: Throwable) {
                Log.e("DictionaryDataSource", "Error while querying the dictionary: ${t.message}")
            }

            override fun onResponse(
                call: Call<DictionaryResponse>,
                response: Response<DictionaryResponse>
            ) {
                val body = response.body()
                if(body != null){
                    Log.i("ASDF", body.toString())
                    callback(body)
                }else{
                    Log.e("DictionaryDataSource", "Error while querying the dictionary: ${response}")
                }
            }
        })
    }
}