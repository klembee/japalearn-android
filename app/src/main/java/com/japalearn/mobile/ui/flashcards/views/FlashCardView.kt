package com.japalearn.mobile.ui.flashcards.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.ui.misc.FuriganaTextView
import com.japalearn.mobile.ui.misc.TextWithFurigana

class FlashCardView (context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {

    private var japaneseRepresentation: FuriganaTextView
    private var meanings: TextView
    private var separator: View
    private var card: Vocab? = null
    private var side: Int = FlashCardEditView.SIDE_FRONT

    init {
        View.inflate(context, R.layout.view_flash_card, this)
        japaneseRepresentation = findViewById(R.id.card_japanese_representation)
        meanings = findViewById(R.id.meanings)
        separator = findViewById(R.id.separator)
    }

    /**
     * Sets the card that this view with contains
     */
    fun setCard(card: Vocab){
        side = FlashCardEditView.SIDE_FRONT
        this.card = card

        var furigana: String? = null
        card.readings?.let {
            if(it.isNotEmpty()){
                furigana = it[0]
            }
        }

        japaneseRepresentation.setText(TextWithFurigana(card.word, furigana))



        //Hide response
        separator.visibility = View.INVISIBLE
        meanings.text = ""

    }

    /**
     * Changes the side of the card
     */
    fun changeSide(){
        when(side){
            FlashCardEditView.SIDE_FRONT -> {
                side = FlashCardEditView.SIDE_BACK

                //Show the meanings
                separator.visibility = View.VISIBLE
                meanings.text = card?.showMeanings()
            }
            FlashCardEditView.SIDE_BACK -> {
                side = FlashCardEditView.SIDE_FRONT

                //Show the word
                separator.visibility = View.INVISIBLE
                meanings.text = ""
            }
        }
    }
}