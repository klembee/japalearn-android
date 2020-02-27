package com.japalearn.mobile.data.api

import com.japalearn.mobile.data.api.auth.AuthApi
import com.japalearn.mobile.data.api.dictionary.DictionaryApi
import com.japalearn.mobile.data.api.feedback.FeedbackApi
import com.japalearn.mobile.data.api.kanji.KanjiApi
import com.japalearn.mobile.data.api.lexic.LexicApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Main class used to call every api endpoints
 * @author Clement Bisaillon
 */
object ApiController {
    private val BASE_URL = "https://japalearn.com/"

    val authApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(AuthApi::class.java)

    val feedbackApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(FeedbackApi::class.java)

    val dictionaryApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(DictionaryApi::class.java)

    val kanjiApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(KanjiApi::class.java)

    val lexicApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(LexicApi::class.java)
}