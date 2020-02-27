package com.japalearn.mobile.ui.misc

class TextWithFurigana(val text: String, val furigana: String?){
    fun getUnformattedText(): String{
        if(furigana == null){
            return "[$text]"
        }else{
            return "[$text{$furigana}]"
        }

    }
}