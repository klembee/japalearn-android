package com.japalearn.mobile.data.api.dictionary

import com.japalearn.mobile.data.models.DictionaryEntry

data class DictionaryResponse (
    val success: Boolean,
    val message: String,
    val entries: List<DictionaryEntry>
)