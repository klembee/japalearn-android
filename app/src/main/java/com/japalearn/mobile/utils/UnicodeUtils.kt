package com.japalearn.mobile.utils

import android.util.Log

object UnicodeUtils {

    fun isKanji(char: Char): Boolean{
        return char.toInt() >= 19968
    }

    fun katakanaToHiragana(katakanaString: String): String{
        var hiraganaString = ""

        katakanaString.forEach {
            val hiragana = it.minus(96)
            hiraganaString += hiragana
        }

        return hiraganaString
    }
}