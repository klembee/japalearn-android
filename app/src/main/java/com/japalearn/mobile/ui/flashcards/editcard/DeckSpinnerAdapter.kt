package com.japalearn.mobile.ui.flashcards.editcard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.japalearn.mobile.data.models.DeckAndAllCards

/**
 * Adapter to show the list of decks in a spinner
 * @author Clement Bisaillon
 */
class DeckSpinnerAdapter(
    context: Context,
    var decks: ArrayList<DeckAndAllCards>
): ArrayAdapter<DeckAndAllCards>(context, 0, decks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val deck = decks[position]

        var newView: View? = null
        if(convertView == null){
            newView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)
        }

        val text = newView?.findViewById<TextView>(android.R.id.text1)
        text?.text = deck.deck.title

        return newView!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val deck = decks[position]
        var newView: View? = null
        if(convertView == null){
            newView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
        }

        val text = newView?.findViewById<TextView>(android.R.id.text1)
        text?.text = deck.deck.title

        if(newView != null){
            return newView
        }else{
            return TextView(context)
        }
    }

    fun changeData(list: List<DeckAndAllCards>){
        clear()
        addAll(list)
        notifyDataSetChanged()
    }
}