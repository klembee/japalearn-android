package com.japalearn.mobile.data.api.feedback

import com.japalearn.mobile.data.api.SuccessResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface FeedbackApi {

    @POST("/api/feedback")
    @FormUrlEncoded
    fun createFeedback(
        @Header("Authorization") auth: String,
        @Field("is_bug") isBug: Boolean,
        @Field("section") section: String,
        @Field("description") description: String
    ): Call<SuccessResponse>
}