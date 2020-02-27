package com.japalearn.mobile.ui.review.lexic.addedit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.DictionaryEntry
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.data.repositories.LexicRepository
import com.japalearn.mobile.ui.dictionary.EntryClickListener

class AddEditLexicActivity: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, EntryClickListener {
    private var lexicRepository: LexicRepository? = null
    private lateinit var editViewModel: EditLexicViewModel

    private lateinit var contentView: FrameLayout
    private lateinit var bottomMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_add_edit_lexic)
        editViewModel = ViewModelProviders.of(this).get(EditLexicViewModel::class.java)
        lexicRepository = LexicRepository.instantiate(this)

        bottomMenu = findViewById(R.id.bottomMenu)
        contentView = findViewById(R.id.contentView)

        editViewModel.dictionaryFragment.itemClickListener = this

        bottomMenu.setOnNavigationItemSelectedListener(this)

        if(editViewModel.currentTab == EditLexicViewModel.TAB_CUSTOM){
            showCustom()
        }else{
            showDictionary()
        }

        editViewModel.word.observe(this, Observer {

        })

        editViewModel.meaning.observe(this, Observer {

        })

        editViewModel.setLexic(intent.getSerializableExtra("lexic") as Vocab?)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_edit_lexic_from_dictionary -> {
                showDictionary()
            }
            R.id.menu_edit_lexic_custom -> {
                showCustom()
            }
        }

        return true
    }

    private fun showDictionary(){
        if(editViewModel.currentTab != EditLexicViewModel.TAB_FROM_DICTIONARY) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentView, editViewModel.dictionaryFragment)
                .commit()
            editViewModel.currentTab = EditLexicViewModel.TAB_FROM_DICTIONARY
        }
    }

    private fun showCustom(){
        if(editViewModel.currentTab != EditLexicViewModel.TAB_CUSTOM) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentView, editViewModel.editCustomLexicFragment)
                .commit()
            editViewModel.currentTab = EditLexicViewModel.TAB_CUSTOM
        }
    }

    private fun saveLexics(){
        editViewModel.save(lexicRepository) {
            finish()
        }
    }

    override fun dictionaryEntryClicked(entry: DictionaryEntry) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
            R.id.menu_save_btn -> saveLexics()
        }
        return super.onOptionsItemSelected(item)
    }
}