package com.japalearn.mobile.data.api.auth

import com.japalearn.mobile.data.api.SuccessResponse
import com.japalearn.mobile.data.api.user.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Authentication interface for the API
 * @author Clement Bisaillon
 */
interface AuthApi {
    @POST("/api/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @POST("/api/auth/register")
    @FormUrlEncoded
    fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @POST("/api/auth/verify")
    fun verify(@Header("Authorization") authToken: String): Call<SuccessResponse>
}