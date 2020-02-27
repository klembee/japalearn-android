package com.japalearn.mobile.ui.review

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.japalearn.mobile.R
import com.japalearn.mobile.data.SyncAdapter
import com.japalearn.mobile.data.repositories.AuthRepository
import com.japalearn.mobile.ui.flashcards.play.FlashCardPlayActivity
import com.japalearn.mobile.ui.review.lexic.addedit.AddEditLexicActivity
import com.japalearn.mobile.ui.review.lexic.LexicListActivity
import java.io.Serializable

class ReviewFragment: Fragment() {

    private lateinit var reviewViewModel: ReviewViewModel

    private lateinit var numberOfReviewRemaining: TextView
    private lateinit var numberOfVocabulary: TextView
    private lateinit var reviewBtn: Button
    private lateinit var viewVocabButton: Button
    private lateinit var addVocFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel::class.java)
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberOfReviewRemaining = view.findViewById(R.id.number_of_review_remaining)
        numberOfVocabulary = view.findViewById(R.id.number_of_vocab)
        reviewBtn = view.findViewById(R.id.review_btn)
        viewVocabButton = view.findViewById(R.id.viewVocabBtn)
        addVocFab = view.findViewById(R.id.add_vocabulary_fab)

        reviewViewModel.lexics.observe(this, Observer {
            numberOfVocabulary.text = it.size.toString()
        })

        reviewViewModel.toReview.observe(this, Observer {
            numberOfReviewRemaining.text = it.size.toString()
        })
        reloadLexic()


        reviewBtn.setOnClickListener {
            val intent = Intent(context, FlashCardPlayActivity::class.java)
            intent.putExtra("deck", reviewViewModel.toReview.value as Serializable)
            startActivity(intent)
        }

        viewVocabButton.setOnClickListener {
            val intent = Intent(context, LexicListActivity::class.java)
            intent.putExtra("lexics", reviewViewModel.lexics.value as Serializable)
            startActivity(intent)
        }

        addVocFab.setOnClickListener {
            gotoAddVocActivity()
        }
    }

    fun gotoAddVocActivity(){
        val intent = Intent(context, AddEditLexicActivity::class.java)
        startActivity(intent)
    }

    fun reloadLexic(){
        reviewViewModel.loadData(context)
    }

    override fun onResume() {
        Log.i("ASDF", "RESUMED")
        super.onResume()
        reloadLexic()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.review_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_sync_btn -> {
                context?.let {
                    val userAccount = AuthRepository.getLoggedInUser(it)
                    SyncAdapter.requestSync(userAccount)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}