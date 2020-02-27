package com.japalearn.mobile.ui.flashcards.play

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.ui.flashcards.play.congratulations.CongratulationsFragment
import com.japalearn.mobile.ui.flashcards.views.FlashCardView
import java.lang.Exception

/**
 * Activity that lets the user learn with the flashcard decks
 * he created
 * @author Clement Bisaillon
 */
class FlashCardPlayActivity: AppCompatActivity() {

    private var playViewModel: FlashCardPlayViewModel? = null

    private lateinit var beforeAnswerLayout: LinearLayout
    private lateinit var afterAnswerLayout: LinearLayout
    private lateinit var cardView: FlashCardView
    private lateinit var showAnswerBtn: Button
    private lateinit var wrongAnswerBtn: Button
    private lateinit var rightAnswerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card_play)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        playViewModel = ViewModelProviders.of(this).get(FlashCardPlayViewModel::class.java)
        playViewModel?.init(this)

        beforeAnswerLayout = findViewById(R.id.beforeAnswerLayout)
        afterAnswerLayout = findViewById(R.id.afterAnswerLayout)
        cardView = findViewById(R.id.flash_card)
        showAnswerBtn = findViewById(R.id.showAnswerBtn)
        wrongAnswerBtn = findViewById(R.id.wrong_answer_btn)
        rightAnswerBtn = findViewById(R.id.right_answer_btn)

        //todo: button state in model
        configureButtons()

        val deckData = intent.getSerializableExtra("deck")
        if(deckData != null){
            try {
                val deck = deckData as ArrayList<Vocab>
                if(deck.isEmpty()){
                    Toast.makeText(this, R.string.no_lexic_to_study, Toast.LENGTH_SHORT).show()
                    finish()
                }

                playViewModel?.currentCard?.observe(this, Observer {
                    if(it != null) {
                        //When the current card changes
                        beforeAnswerLayout.visibility = View.VISIBLE
                        afterAnswerLayout.visibility = View.INVISIBLE
                        cardView.setCard(it)
                    }else{
                        //No more cards
                        stopGame()
                    }
                })

                playViewModel?.setDeck(deck)
            }catch (e: Exception){

            }
        }else{
            finish()
        }
    }

    private fun configureButtons(){
        showAnswerBtn.setOnClickListener {
            showAnswer()
        }

        wrongAnswerBtn.setOnClickListener {
            playViewModel?.nextCard(false)
        }

        rightAnswerBtn.setOnClickListener {
            playViewModel?.nextCard(true)
        }
    }

    /**
     * Flip the card over to show the answer
     */
    private fun showAnswer(){
        beforeAnswerLayout.visibility = View.INVISIBLE
        afterAnswerLayout.visibility = View.VISIBLE
        cardView.changeSide()
    }

    fun stopGame(showCongrats: Boolean = true){
        //Show congratulations
        if(showCongrats) {
            playViewModel?.saveProgress()

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.content,
                    CongratulationsFragment(
                        playViewModel?.gotRight ?: emptySet(),
                        playViewModel?.gotWrong ?: emptySet()
                    )
                )
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        stopGame(false)
        super.onBackPressed()
    }
}