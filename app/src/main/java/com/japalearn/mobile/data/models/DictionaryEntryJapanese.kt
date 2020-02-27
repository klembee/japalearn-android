package com.japalearn.mobile.data.models

import java.io.Serializable

data class DictionaryEntryJapanese(
    val id: Int,
    val representation: String,
    val information: String,
    val categories: String,
    val kanaRepresentations: List<DictionaryEntryKana>
): Serializable