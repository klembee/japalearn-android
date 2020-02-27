package com.japalearn.mobile.ui.dictionary

import com.japalearn.mobile.data.models.DictionaryEntry

interface EntryClickListener {
    fun dictionaryEntryClicked(entry: DictionaryEntry)
}