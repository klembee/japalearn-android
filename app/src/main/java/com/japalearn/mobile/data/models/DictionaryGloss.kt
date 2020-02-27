package com.japalearn.mobile.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DictionaryGloss (
    val meaning: String,
    val applies_to: String,
    @SerializedName("see_also")
    val seeAlso: String,
    val antonym: String,
    val types: String,
    val field: String,
    val misc: String,
    val dialect: String,
    val information: String
): Serializable