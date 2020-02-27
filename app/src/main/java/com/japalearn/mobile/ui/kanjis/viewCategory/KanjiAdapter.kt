package com.japalearn.mobile.ui.kanjis.viewCategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Kanji

/**
 * Adapter to show a list of kanjis
 * @author Clement Bisaillon
 */
class KanjiAdapter(
    private var kanjis: List<Kanji> = emptyList(),
    val onClick: (Kanji) -> Unit
): RecyclerView.Adapter<KanjiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return ViewHolder(inflater.inflate(R.layout.item_kanji, parent, false))
    }

    override fun getItemCount(): Int {
        return kanjis.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(kanjis[position])
    }

    /**
     * Updates the list of kanji to display
     */
    fun updateData(kanjis: List<Kanji>){
        val oldSize = this.kanjis.size
        this.kanjis = kanjis

        notifyItemRangeChanged(0, oldSize)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){

        var literalTxt: TextView? = null
        var firstMeaningTxt: TextView? = null

        init {
            literalTxt = view.findViewById(R.id.kanji_item_literal)
            firstMeaningTxt = view.findViewById(R.id.kanji_item_meaning)
        }

        fun bind(kanji: Kanji){
            literalTxt?.text = kanji.literal
            firstMeaningTxt?.text = kanji.meanings[0]

            view.setOnClickListener {
                onClick(kanji)
            }
        }
    }
}