package com.japalearn.mobile.ui.review.lexic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.ui.misc.FuriganaTextView
import com.japalearn.mobile.ui.misc.TextWithFurigana
import com.japalearn.mobile.utils.TimeUtil

class LexicListAdapter(
    val context: Context,
    var vocabs: List<Vocab>
): RecyclerView.Adapter<LexicListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.item_lexic_list, parent, false))
    }

    override fun getItemCount(): Int {
        return vocabs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(vocabs[position])
    }

    fun changeLexic(list: List<Vocab>){
        val oldSize = vocabs.size
        vocabs = list
        notifyItemRangeChanged(0, oldSize)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        private var wordTxt: FuriganaTextView? = null
        private var meaningTxt: TextView? = null
        private var levelTxt: TextView? = null
        private var nextReviewTxt: TextView? = null
        private var otherMeaningsTxt: TextView? = null

        init {
            wordTxt = view.findViewById(R.id.lexic_word_txt)
            meaningTxt = view.findViewById(R.id.lexic_meaning_txt)
            levelTxt = view.findViewById(R.id.lexic_level_txt)
            nextReviewTxt = view.findViewById(R.id.lexic_next_review_txt)
            otherMeaningsTxt = view.findViewById(R.id.item_has_other_meanings_txt)
        }

        fun bind(vocab: Vocab){
            wordTxt?.setText(TextWithFurigana(vocab.word, vocab.readings?.get(0)))
            levelTxt?.text = context.getString(R.string.item_level_txt, vocab.level)
            meaningTxt?.text = vocab.meanings[0]

            if(vocab.meanings.size > 1){
                otherMeaningsTxt?.visibility = View.VISIBLE
            }

            val nextReviewIn = vocab.timeUntilNextReview()
            if(nextReviewIn == 0L){
                nextReviewTxt?.text = context.getString(R.string.item_ready_for_review)
            }else{
                nextReviewTxt?.text = context.getString(R.string.item_next_review_time, TimeUtil.durationToHumanReadable(context, nextReviewIn))
            }

        }
    }
}