package com.japalearn.mobile.data.api.kanji

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KanjiApi {

    @GET("/api/kanji/categories")
    fun getCategories(): Call<KanjiCategoryResponse>

    @GET("/api/kanji/categories/view")
    fun getKanjisInCategory(@Query("category") category: String): Call<KanjiResponse>

    @GET("/api/kanji/{kanji}")
    fun getKanji(@Path("kanji") kanjiId: Int): Call<KanjiResponse>
}