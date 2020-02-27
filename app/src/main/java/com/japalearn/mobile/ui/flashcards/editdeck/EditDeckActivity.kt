package com.japalearn.mobile.ui.flashcards.editdeck

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProviders
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.repositories.DeckRepository

/**
 * Activity allowing the user to create and edit a deck
 * @author Clement Bisaillon
 */
class EditDeckActivity: AppCompatActivity() {
    private var deckRepository: DeckRepository? = null
    private lateinit var editDeckViewModel: EditDeckViewModel
    private lateinit var titleTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editDeckViewModel = ViewModelProviders.of(this).get(EditDeckViewModel::class.java)
        setContentView(R.layout.activity_edit_card_deck)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        deckRepository = DeckRepository.instantiate(this)

        titleTxt = findViewById(R.id.edit_deck_title_txt)
        titleTxt.doAfterTextChanged {
            editDeckViewModel.setTitle(it.toString())
        }

        //Get the deck to edit if present
        val deckData = intent.getSerializableExtra("deck")
        deckData?.let {
            val deck = deckData as DeckAndAllCards
            editDeckViewModel.setDeck(deck)
            titleTxt.setText(deck.deck.title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save_btn -> save()
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun save(){
        editDeckViewModel.save(deckRepository){
            finish()
        }
    }
}