package com.japalearn.mobile.ui.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.DeckAndAllCards

/**
 * Adapter for the flash card list
 * @author Clement Bisaillon
 */
class FlashCardDeckAdapter(
    var decks: List<DeckAndAllCards>,
    val onPlayClicked: (deck: DeckAndAllCards) -> Unit,
    val onEditClicked: (deck: DeckAndAllCards) -> Unit
): RecyclerView.Adapter<FlashCardDeckAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_flash_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return decks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(decks[position])
    }

    fun changeDataSet(newDecks: List<DeckAndAllCards>){
        val oldSize = decks.size
        decks = newDecks
        notifyItemRangeChanged(0, oldSize)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private var deckNameTxt: TextView? = null
        private var numberOfCardsTxt: TextView? = null
        private var editBtn: ImageView? = null

        init {
            deckNameTxt = view.findViewById(R.id.deck_name_txt)
            numberOfCardsTxt = view.findViewById(R.id.number_cards_txt)
            editBtn = view.findViewById(R.id.edit_btn)
        }

        fun bind(deck: DeckAndAllCards){
            deckNameTxt?.text = deck.deck.title
            numberOfCardsTxt?.text = view.resources.getQuantityString(R.plurals.deck_item_number_of_cards, deck.cards.size, deck.cards.size)

            view.setOnClickListener { onPlayClicked(deck) }
            editBtn?.setOnClickListener { onEditClicked(deck) }
        }
    }
}