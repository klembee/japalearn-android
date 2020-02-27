package com.japalearn.mobile.data.models

import com.google.gson.annotations.SerializedName
import com.japalearn.mobile.data.models.learning.Kanji
import java.io.Serializable

/**
 * Represents a dictionary entry from the API
 * @author Clement Bisaillon
 */
data class DictionaryEntry (
    val id: Int,
    val ent_seq: Int,
    val relevence: Int,
    val frequency: Int,
    @SerializedName("japanese_representations")
    val japaneseRepresentations: List<DictionaryEntryJapanese>,

    @SerializedName("kana_representations")
    val kanaRepresentations: List<DictionaryEntryKana>,

    val meanings: List<DictionaryGloss>,
    val kanjis: List<Kanji>?
): Serializable{

    fun getMeaningsList(): List<String>{
        val _meanings = ArrayList<String>()
        meanings.forEach { _meanings.add(it.meaning) }
        return _meanings
    }

    fun getKanaRepresentationsList(): List<String>{
        val _representations = ArrayList<String>()
        kanaRepresentations.forEach { _representations.add(it.representation) }
        return _representations
    }
}