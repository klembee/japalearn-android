package com.japalearn.mobile.ui.flashcards.editcard

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.japalearn.mobile.R
import com.japalearn.mobile.data.repositories.DeckRepository
import com.japalearn.mobile.data.repositories.FlashCardRepository
import com.japalearn.mobile.data.repositories.LexicRepository
import com.japalearn.mobile.ui.flashcards.views.FlashCardEditView

/**
 * Activity allowing the user to create of edit a flash card
 * @author Clement Bisaillon
 */
class EditFlashCardActivity: AppCompatActivity() {

    private var deckRepository: DeckRepository? = null
    private var cardRepository: FlashCardRepository? = null
    private var lexicRepository: LexicRepository? = null

    private lateinit var flashCardViewModel: EditFlashCardViewModel
    private lateinit var frontCard: FlashCardEditView
    private lateinit var backCard: FlashCardEditView
    private lateinit var deckChoiceSpinner: Spinner
    private var deckChoiceAdapter: DeckSpinnerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flashCardViewModel = ViewModelProviders.of(this).get(EditFlashCardViewModel::class.java)
        setContentView(R.layout.activity_create_flash_card)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        deckRepository = DeckRepository.instantiate(this)
        cardRepository = FlashCardRepository.instantiate(this)
        lexicRepository = LexicRepository.instantiate(this)

        deckChoiceSpinner = findViewById(R.id.deck_choice_spinner)
        frontCard = findViewById(R.id.flash_card_edit_front)
        backCard = findViewById(R.id.flash_card_edit_back)

        setupDeckChoiceSpinner()
        setupCards()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun setupDeckChoiceSpinner(){
        deckChoiceAdapter = DeckSpinnerAdapter(this, ArrayList())
        deckChoiceSpinner.adapter = deckChoiceAdapter
        deckChoiceSpinner.onItemSelectedListener = flashCardViewModel

        val deckData = deckRepository?.getDecks()
        deckData?.observe(this, Observer {
            flashCardViewModel.setDecks(it)
            deckChoiceAdapter?.changeData(it)
        })
    }

    private fun setupCards(){
        frontCard.addOnTextChanged {
            flashCardViewModel.setFrontContent(it.toString())
        }

        backCard.addOnTextChanged {
            flashCardViewModel.setBackContent(it.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save_btn -> saveCard()
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Save the card to the database
     */
    private fun saveCard(){
        flashCardViewModel.save(cardRepository, lexicRepository){
            frontCard.reset()
            backCard.reset()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}