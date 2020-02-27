package com.japalearn.mobile.data.models

import com.google.gson.annotations.SerializedName

data class KanjiCategory(
    val category: String,
    @SerializedName("nb_kanjis")
    val nbKanjis: Int
)