package com.japalearn.mobile.ui.review.lexic

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Vocab

class LexicListActivity: AppCompatActivity() {

    private lateinit var lexicListViewModel: LexicListViewModel
    private lateinit var lexicListRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lexic_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        lexicListRV = findViewById(R.id.lexicListRV)

        lexicListViewModel = ViewModelProviders.of(this).get(LexicListViewModel::class.java)

        lexicListViewModel.lexics.observe(this, Observer {

        })

        lexicListViewModel.setLexics(this, intent.getSerializableExtra("lexics") as List<Vocab>)
        setupList()
    }

    private fun setupList(){
        lexicListRV.adapter = lexicListViewModel.listAdapter
        lexicListRV.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}