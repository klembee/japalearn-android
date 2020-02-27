package com.magestionplus.android.ui.flashcards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.DeckAndAllCards
import com.japalearn.mobile.data.repositories.DeckRepository
import com.japalearn.mobile.ui.flashcards.FlashCardDeckAdapter
import com.japalearn.mobile.ui.flashcards.FlashCardsViewModel
import com.japalearn.mobile.ui.flashcards.editcard.EditFlashCardActivity
import com.japalearn.mobile.ui.flashcards.editdeck.EditDeckActivity
import com.japalearn.mobile.ui.flashcards.play.FlashCardPlayActivity

/**
 * Fragment that allows the user to learn vocabulary with flash cards
 * @author Clement Bisaillon
 */
class FlashCardsFragment: Fragment() {

    private var deckRepository: DeckRepository? = null
    private lateinit var flashCardsViewModel: FlashCardsViewModel
    private lateinit var flashCardsRecyclerView: RecyclerView
    private var deckAdapter: FlashCardDeckAdapter? = null

    private lateinit var fab: FloatingActionButton
    private lateinit var fabMenu: LinearLayout
    private lateinit var newDeckBtn: Button
    private lateinit var newCardBtn: Button
    private var fabMenuVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        flashCardsViewModel = ViewModelProviders.of(this).get(FlashCardsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_flash_cards, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckRepository = DeckRepository.instantiate(context)
        flashCardsRecyclerView = view.findViewById(R.id.flash_card_list_recyclerview)
        fab = view.findViewById(R.id.fab)
        fabMenu = view.findViewById(R.id.fab_menu)
        newDeckBtn = view.findViewById(R.id.new_deck_button)
        newCardBtn = view.findViewById(R.id.new_card_btn)

        configureFab()
        configureRecyclerView()
    }

    private fun configureFab(){
        fab.setOnClickListener {
            fabMenuVisible = !fabMenuVisible
            if(fabMenuVisible){
                fabMenu.visibility = View.VISIBLE
            }else{
                fabMenu.visibility = View.GONE
            }
        }

        newDeckBtn.setOnClickListener {
            gotoEditDeck()
        }

        newCardBtn.setOnClickListener {
            gotoEditCard()
        }
    }

    /**
     * Go to the activity to play the selected deck
     */
    private fun gotoPlayDeck(deck: DeckAndAllCards){
        val intent = Intent(context, FlashCardPlayActivity::class.java)
        intent.putExtra("deck", deck)

        startActivity(intent)
    }

    /**
     * Go to the activity to edit a card
     */
    private fun gotoEditCard(){
        val intent = Intent(context, EditFlashCardActivity::class.java)

        startActivity(intent)
    }

    /**
     * Go to the activity to edit the selected deck
     */
    private fun gotoEditDeck(deck: DeckAndAllCards? = null){
        val intent = Intent(context, EditDeckActivity::class.java)

        deck?.let {
            intent.putExtra("deck", deck)
        }

        startActivity(intent)
    }

    /**
     * Configures the recycler view
     */
    private fun configureRecyclerView(){
        deckAdapter = FlashCardDeckAdapter(emptyList(), this::onPlayClicked, this::onEditClicked)
        reloadDecks()

        flashCardsRecyclerView.adapter = deckAdapter
        flashCardsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    /**
     * When the user clicks on play button in the list
     */
    private fun onPlayClicked(deck: DeckAndAllCards){
        gotoPlayDeck(deck)
    }

    /**
     * When the user clicks on a edit deck button in the list
     */
    private fun onEditClicked(deck: DeckAndAllCards){
        gotoEditDeck(deck)
    }

    private fun closeFabMenu(){
        fabMenuVisible = false
        fabMenu.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        closeFabMenu()
        reloadDecks()
    }

    /**
     * Reload the decks from the repository
     */
    private fun reloadDecks(){
        val decks = deckRepository?.getDecks()
        decks?.observe(this, Observer {
            deckAdapter?.changeDataSet(it)
        })
    }
}