package com.japalearn.mobile.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DictionaryEntryKana (
    val id: Int,
    val representation: String,
    @SerializedName("true_reading")
    val trueReading: Int,
    val information: String,
    val categogies: String
): Serializable