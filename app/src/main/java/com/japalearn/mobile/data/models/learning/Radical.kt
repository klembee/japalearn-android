package com.japalearn.mobile.data.models.learning

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "radicals")
data class Radical (
    @PrimaryKey
    val id: Int,
    @SerializedName("required_level")
    val requiredLevel: Int,
    val level: Int,

    @SerializedName("last_time_studied")
    val lastTimeStudied: Long? = null,

    var synced: Boolean = false,
    var deleted: Boolean = false
)