package com.japalearn.mobile.ui.kanjis.categories


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.KanjiCategory

/**
 * Adapter for the list of kanji categories
 * @author Clement Bisaillon
 */
class KanjiCategoryAdapter(
    val context: Context?,
    var categories: List<KanjiCategory>,
    val onClick: (KanjiCategory) -> Unit
): RecyclerView.Adapter<KanjiCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.item_kanji_category_item, parent, false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    fun updateData(categories: List<KanjiCategory>){
        val oldSize = this.categories.size
        this.categories = categories
        notifyItemRangeChanged(0, oldSize)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private var title: TextView? = null
        private var numberOfKanjiTxt: TextView? = null

        init {
            title = view.findViewById(R.id.kanji_category_title)
            numberOfKanjiTxt = view.findViewById(R.id.kanji_category_nb_kanjis)
        }

        fun bind(category: KanjiCategory){
            title?.text = category.category
            context?.let {
                numberOfKanjiTxt?.text = it.resources.getQuantityString(R.plurals.kanji_category_list_number_of_kanjis, category.nbKanjis, category.nbKanjis)
            }

            view.setOnClickListener { onClick(category) }
        }
    }
}