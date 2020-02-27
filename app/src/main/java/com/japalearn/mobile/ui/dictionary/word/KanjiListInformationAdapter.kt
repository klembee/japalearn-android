package com.japalearn.mobile.ui.dictionary.word

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Kanji

class KanjiListInformationAdapter(
    var kanjis: List<Kanji>
): RecyclerView.Adapter<KanjiListInformationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.item_kanji_information, parent, false))
    }

    override fun getItemCount(): Int {
        return kanjis.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(kanjis[position])
    }


    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){

        private var literal: TextView? = null
        private var meanings: TextView? = null
        private var kunReadings: TextView? = null
        private var onReadings: TextView? = null

        init{
            literal = view.findViewById(R.id.kanji_information_literal)
            meanings = view.findViewById(R.id.kanji_information_meanings)
            kunReadings = view.findViewById(R.id.kanji_information_kun_readings)
            onReadings = view.findViewById(R.id.kanji_information_on_readings)
        }

        fun bind(kanji: Kanji){
            literal?.text = kanji.literal
            meanings?.text = kanji.meanings.joinToString()
            kunReadings?.text =  kanji.kunReadings?.joinToString()
            onReadings?.text = kanji.onReadings?.joinToString()
        }
    }
}