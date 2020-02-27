package com.japalearn.mobile.data.api

/**
 * Represents the most basic response from the API.
 * It only contains the status and a message.
 * @author Clement Bisaillon
 */
data class SuccessResponse (
    val success: Boolean,
    val message: String
)