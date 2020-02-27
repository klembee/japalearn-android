package com.japalearn.mobile.data.models.learning

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Kanji (
    @PrimaryKey
    val id: Int,
    val literal: String,
    @SerializedName("required_level")
    val requiredLevel: Int,
    val level : Int,
    val grade: Int?,
    val note: String,
    @SerializedName("stroke_count")
    val strokeCount: Int?,
    val frequency: Int?,
    @SerializedName("jlpt_level")
    val jlptLevel: Int?,
    val category: String?,

    //The most common meaning
    val meaning: String,

    //All of the meanings
    val meanings: List<String>,

    @SerializedName("last_time_studied")
    val lastTimeStudied: Long? = null,

    @SerializedName("on_readings")
    val onReadings: List<String>?,
    @SerializedName("kun_readings")
    val kunReadings: List<String>?,

    var synced: Boolean = false,
    var deleted: Boolean = false
): Serializable