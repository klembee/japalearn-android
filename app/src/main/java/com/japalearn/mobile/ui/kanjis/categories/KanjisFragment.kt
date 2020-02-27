package com.japalearn.mobile.ui.kanjis.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.KanjiCategory
import com.japalearn.mobile.data.repositories.KanjiRepository
import com.japalearn.mobile.ui.kanjis.viewCategory.ViewCategoryActivity

/**
 * Fragment that shows a list of usefull kanjis
 * @author Clement Bisaillon
 */
class KanjisFragment: Fragment() {

    private var kanjiRepository: KanjiRepository? = null
    private var kanjiVieWModel: KanjisFragmentViewModel? = null
    private lateinit var kanjiRecyclerView: RecyclerView
    private var categoriesAdapter: KanjiCategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kanjiVieWModel = ViewModelProviders.of(this).get(KanjisFragmentViewModel::class.java)
        kanjiRepository = KanjiRepository.instantiate(context)

        return inflater.inflate(R.layout.fragment_kanjis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kanjiRecyclerView = view.findViewById(R.id.kanjiRecyclerView)

        configureKanjiList()
    }

    private fun configureKanjiList(){
        categoriesAdapter =
            KanjiCategoryAdapter(context, emptyList(), this::categoryClicked)
        kanjiRecyclerView.adapter = categoriesAdapter
        kanjiRecyclerView.layoutManager = LinearLayoutManager(context)

        kanjiRepository?.getCategories()?.observe(this, Observer {
            updateCategories(it)
        })
    }

    /**
     * When the user clicks a category
     */
    fun categoryClicked(category: KanjiCategory){
        val intent = Intent(context, ViewCategoryActivity::class.java)
        intent.putExtra("category", category.category)
        startActivity(intent)
    }

    fun updateCategories(categories: List<KanjiCategory>){
        categoriesAdapter?.updateData(categories)
    }
}