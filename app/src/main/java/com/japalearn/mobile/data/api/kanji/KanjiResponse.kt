package com.japalearn.mobile.data.api.kanji

import com.japalearn.mobile.data.models.learning.Kanji

data class KanjiResponse (
    val success: Boolean,
    val message: String,
    val kanjis: List<Kanji>
)