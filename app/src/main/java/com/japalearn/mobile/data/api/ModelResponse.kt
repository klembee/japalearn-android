package com.japalearn.mobile.data.api

data class ModelResponse<T> (
    val success: Boolean,
    val message: String,
    val models: List<T>
)