package com.japalearn.mobile.ui.flashcards.views

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.japalearn.mobile.R

class FlashCardEditView(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {

    private var text: EditText
    private var sideIndicationTxt: TextView

    init {
        View.inflate(context, R.layout.view_flash_card_edit, this)
        text = findViewById(R.id.card_edit_text)
        sideIndicationTxt = findViewById(R.id.card_edit_side_indication)

        applyAttributes(context, attributeSet)
    }

    private fun applyAttributes(context: Context, attributeSet: AttributeSet){
        val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.FlashCardEditView, 0, 0)
        val hint = typedArray.getString(R.styleable.FlashCardEditView_hint)
        val side = typedArray.getInteger(R.styleable.FlashCardEditView_side, 0)

        hint?.let {
            text.hint = hint
        }

        when(side){
            SIDE_FRONT -> sideIndicationTxt.text = context.getString(R.string.edit_flash_card_front)
            SIDE_BACK -> sideIndicationTxt.text = context.getString(R.string.edit_flash_card_back)
        }

        typedArray.recycle()
    }

    /**
     * Reset the text of the card
     */
    fun reset(){
        text.text.clear()
    }

    /**
     * Add a listener for the on text changed event
     */
    fun addOnTextChanged(function: (text: Editable?) -> Unit){
        text.doAfterTextChanged(function)
    }

    companion object {
        val SIDE_FRONT = 0
        val SIDE_BACK = 1
    }
}