package com.japalearn.mobile.ui.kanjis.viewKanji

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Kanji
import com.japalearn.mobile.data.repositories.KanjiRepository

/**
 * Activity that displays information about a specific kanji
 */
class KanjiViewActivity: AppCompatActivity() {

    private var kanjiRepository: KanjiRepository? = null
    private var kanjiViewModel: KanjiViewModel? = null
    private lateinit var literalTxt: TextView
    private lateinit var onReadingsTxt: TextView
    private lateinit var kunReadingsTxt: TextView
    private lateinit var nbStrokesTxt: TextView
    private lateinit var meaningsTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_kanji)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        kanjiViewModel = ViewModelProviders.of(this).get(KanjiViewModel::class.java)
        kanjiRepository = KanjiRepository.instantiate(this)

        literalTxt = findViewById(R.id.kanji_view_literal)
        onReadingsTxt = findViewById(R.id.kanji_view_on_readings)
        kunReadingsTxt = findViewById(R.id.kanji_view_kun_readings)
        nbStrokesTxt = findViewById(R.id.kanji_view_nb_strokes)
        meaningsTxt = findViewById(R.id.kanji_view_meanings)

        val kanjiId = intent.getIntExtra("kanji_id", -1)
        if(kanjiId == -1) finish()

        kanjiRepository?.getKanji(kanjiId)?.observe(this, Observer {
            kanjiViewModel?.setKanji(it)
        })

        kanjiViewModel?.kanji?.observe(this, Observer {
            displayKanji(it)
        })
    }

    private fun displayKanji(kanji: Kanji){
        literalTxt.text = kanji.literal
        nbStrokesTxt.text = resources.getQuantityString(R.plurals.kanji_view_nb_strokes, kanji.strokeCount?: 0, kanji.strokeCount)

        var kuns = ""
        kanji.kunReadings?.let {
            for(i in it.indices){
                kuns += "${it[i]}; "
            }
        }
        kunReadingsTxt.text = kuns

        var ons = ""
        kanji.onReadings?.let {
            for(i in it.indices){
                ons += "${it[i]}; "
            }
        }
        onReadingsTxt.text = ons


        var meanings = ""
        for (i in kanji.meanings.indices) {
            meanings += "${i + 1}. ${kanji.meanings[i]}\n"
        }
        meaningsTxt.text = meanings
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}