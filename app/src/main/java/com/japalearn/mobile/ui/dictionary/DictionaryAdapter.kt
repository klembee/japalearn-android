package com.japalearn.mobile.ui.dictionary

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.DictionaryEntry
import com.japalearn.mobile.ui.misc.FuriganaTextView
import com.japalearn.mobile.ui.misc.TextWithFurigana
import com.japalearn.mobile.utils.UnicodeUtils

/**
 * Adapter for the list of dictionary entries
 * @author Clement Bisaillon
 */
class DictionaryAdapter(
    private val context: Context?,
    private var entries: List<DictionaryEntry>,
    private var onClick: (word: DictionaryEntry) -> Unit
): RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {

    private val selectedEntries = HashSet<DictionaryEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_dictionary_result, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, entries[position], onClick)
    }

    fun changeEntries(entries: List<DictionaryEntry>){
        val oldSize = this.entries.size
        this.entries = entries
        notifyItemRangeChanged(0, oldSize)
        notifyDataSetChanged()
    }

    fun setSelectedEntries(selected: Set<DictionaryEntry>){
        val oldSize = this.entries.size
        val added = ArrayList<DictionaryEntry>()
        val removed = ArrayList<DictionaryEntry>()

        selectedEntries.forEach {
            if(selected.contains(it)){
                //nothing
            }
            else if(!selected.contains(it)){
                //Removed
                removed.add(it)
            }
        }
        selected.forEach {
            if(!selectedEntries.contains(it)){
                //Added
                added.add(it)
            }
        }

        selectedEntries.clear()
        selectedEntries.addAll(selected)

        added.forEach {
            notifyItemChanged(entries.indexOf(it))
        }
        removed.forEach {
            notifyItemChanged(entries.indexOf(it))
        }
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private var japaneseRepresentationTxt: FuriganaTextView? = null
        private var kanaRepresentationTxt: TextView? = null
        private var meaningLayout: LinearLayout? = null
        private var checkedImg: ImageView? = null

        init {
            japaneseRepresentationTxt = view.findViewById(R.id.japanese_representation)
            kanaRepresentationTxt = view.findViewById(R.id.kana_representation)
            meaningLayout = view.findViewById(R.id.meaningLayout)
            checkedImg = view.findViewById(R.id.item_checked)
        }

        fun bind(context: Context?, entry: DictionaryEntry, onClick: (DictionaryEntry) -> Unit){
            var text = ""
            var furigana = ""

            if(entry.japaneseRepresentations.isNotEmpty()){
                text = entry.japaneseRepresentations[0].representation
            }
            if(entry.kanaRepresentations.isNotEmpty()){
                if(entry.japaneseRepresentations.isEmpty()) {
                    text = entry.kanaRepresentations[0].representation
                }else{
                    furigana = entry.kanaRepresentations[0].representation
                }
            }

            japaneseRepresentationTxt?.setText(TextWithFurigana(text, furigana))

            //Reset the meaningLayout
            meaningLayout?.removeAllViews()
            context?.let {
                //Add the meanings
                var index = 1
                entry.meanings.forEach {
                    val txtView = TextView(context)
                    txtView.text = "${index}. ${it.meaning}"

                    meaningLayout?.addView(txtView)

                    index++
                }
            }

            if(selectedEntries.contains(entry)){
                checkedImg?.visibility = View.VISIBLE
            }else{
                checkedImg?.visibility = View.GONE
            }

            view.setOnClickListener { onClick(entry) }

        }
    }
}