package com.japalearn.mobile.data.api.user

import com.japalearn.mobile.data.models.User

/**
 * Represents a user response from the API
 * @author Clement Bisaillon
 */
data class UserResponse (
    val success: Boolean,
    val message: String,
    val user: User?
)