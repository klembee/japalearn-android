package com.japalearn.mobile.data.api.kanji

import com.japalearn.mobile.data.models.KanjiCategory

data class KanjiCategoryResponse (
    val success: Boolean,
    val message: String,
    val categories: List<KanjiCategory>
)