package com.japalearn.mobile.data.api.dictionary

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface DictionaryApi {
    @GET("/api/dictionary")
    fun search(
        @Query("query") query: String
    ): Call<DictionaryResponse>
}