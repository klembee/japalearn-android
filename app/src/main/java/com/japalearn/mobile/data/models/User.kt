package com.japalearn.mobile.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Represents a user from the API
 * @author Clement Bisaillon
 */
@Entity
data class User (
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val level: Int,

    @SerializedName("access_token")
    val authToken: String
)