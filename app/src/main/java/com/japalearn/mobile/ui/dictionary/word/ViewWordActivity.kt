package com.japalearn.mobile.ui.dictionary.word

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.DictionaryEntry

/**
 * Activity that lets the user view more information about a word
 * @author Clement Bisaillon
 */
class ViewWordActivity: AppCompatActivity() {

    private lateinit var word: DictionaryEntry
    private lateinit var representation: TextView
    private lateinit var meanings: TextView
    private lateinit var kanjiList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_view_word)

        representation = findViewById(R.id.view_word_representation)
        meanings = findViewById(R.id.view_word_meanings)
        kanjiList = findViewById(R.id.kanji_in_word_list)

        //Retrieve the word to display
        val intentWord = intent.getSerializableExtra("word")
        if(intentWord != null){
            word = intentWord as DictionaryEntry
        }else{
            Toast.makeText(this, "You didn't clicked a word", Toast.LENGTH_SHORT).show()
            finish()
        }

        configureHeader()
        configureKanjis()
    }

    private fun configureHeader(){
        if(word.japaneseRepresentations.isNotEmpty()) {
            representation.text = word.japaneseRepresentations[0].representation
        }else{
            if(word.kanaRepresentations.isNotEmpty()){
                representation.text = word.kanaRepresentations[0].representation
            }
        }

        //Add the meanings as a list
        var meaningsTxt = ""
        var index = 1
        word.meanings.forEach {
            meaningsTxt += "$index. ${it.meaning}"
            if(++index <= word.meanings.size){
                meaningsTxt += "\n"
            }
        }
        meanings.text = meaningsTxt
    }

    private fun configureKanjis(){
        val adapter = KanjiListInformationAdapter(word.kanjis?: emptyList())
        kanjiList.adapter = adapter
        kanjiList.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}