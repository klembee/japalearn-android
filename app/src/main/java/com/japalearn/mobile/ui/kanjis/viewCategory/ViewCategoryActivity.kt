package com.japalearn.mobile.ui.kanjis.viewCategory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Kanji
import com.japalearn.mobile.data.repositories.KanjiRepository
import com.japalearn.mobile.ui.kanjis.viewKanji.KanjiViewActivity

/**
 * Activity showing the content of a kanji category
 * @author Clement Bisaillon
 */
class ViewCategoryActivity: AppCompatActivity() {

    private var kanjiRepository: KanjiRepository? = null
    private lateinit var kanjiRecyclerView: RecyclerView
    private val kanjiAdapter: KanjiAdapter = KanjiAdapter(emptyList(), this::kanjiClicked)
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_kanji_category)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        kanjiRepository = KanjiRepository.instantiate(this)

        //Get the category
        category = intent.getStringExtra("category")

        kanjiRecyclerView = findViewById(R.id.kanjis_list)
        configureRecyclerView()
    }

    private fun configureRecyclerView(){
        category?.let {
            kanjiRepository?.getKanjisOfCategory(it)?.observe(this, Observer {
                kanjiAdapter.updateData(it)
            })
        }

        kanjiRecyclerView.adapter = kanjiAdapter
        kanjiRecyclerView.layoutManager = GridLayoutManager(this, 5)
    }

    /**
     * When the user clicks a kanji
     */
    private fun kanjiClicked(kanji: Kanji){
        val intent = Intent(this, KanjiViewActivity::class.java)
        intent.putExtra("kanji_id", kanji.id)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}