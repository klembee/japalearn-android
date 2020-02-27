package com.japalearn.mobile.data.api.lexic

import com.japalearn.mobile.data.api.ModelResponse
import com.japalearn.mobile.data.models.learning.Vocab
import retrofit2.Call
import retrofit2.http.*

interface LexicApi {
    @POST("/api/lexic/sync")
    @FormUrlEncoded
    fun sync(
        @Header("Authorization") authToken: String,
        @Field("lexics") lexics: String,
        @Field("last_request_date") lastRequestDate: Long
    ): Call<ModelResponse<Vocab>>
}