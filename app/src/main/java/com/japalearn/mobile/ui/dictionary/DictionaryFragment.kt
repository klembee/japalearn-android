package com.japalearn.mobile.ui.dictionary

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.japalearn.mobile.data.repositories.DictionaryRepository
import android.content.Intent
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.DictionaryEntry
import com.japalearn.mobile.ui.dictionary.word.ViewWordActivity
import com.japalearn.mobile.ui.misc.FuriganaTextView


/**
 * Fragment that allows the user to search the dictionary and view results
 * @author Clement Bisaillon
 */
class DictionaryFragment(
    val mode: Int = MODE_INFORMATION
) : Fragment() {

    private var dictionaryRepository: DictionaryRepository? = null

    private lateinit var dictionaryViewModel: DictionaryViewModel
    private lateinit var resultRecyclerView: RecyclerView
    private lateinit var searchBar: EditText
    private lateinit var searchButton: Button
    private lateinit var loadingView: ProgressBar

    private var lastSearchTime = System.currentTimeMillis()
    var itemClickListener: EntryClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dictionary, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            dictionaryViewModel =
                ViewModelProviders.of(it).get(DictionaryViewModel::class.java)

            dictionaryRepository = DictionaryRepository.instantiate()
            resultRecyclerView = view.findViewById(R.id.dictionaryResultRecyclerView)
            searchBar = view.findViewById(R.id.queryField)
            searchButton = view.findViewById(R.id.searchBtn)
            loadingView = view.findViewById(R.id.loadingView)

            configureRecyclerView()
            configureSearchBar()
        }
    }

    private fun configureSearchBar(){
        searchButton.setOnClickListener {
            showLoading()
            dictionaryRepository?.search(searchBar.text.toString()){
                if(it.success){
                    dictionaryViewModel.setEntries(it.entries)
                }
                hideLoading()
            }

            //Dismiss the keyboard
            val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    private fun showLoading(){
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        loadingView.visibility = View.GONE
    }

    private fun configureRecyclerView(){
        dictionaryViewModel.adapter = DictionaryAdapter(context, emptyList(), this::wordClicked)
        resultRecyclerView.adapter = dictionaryViewModel.adapter
        resultRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    /**
     * Called when the user clicks a word in the recyclerview
     */
    private fun wordClicked(word: DictionaryEntry){
        if(mode == MODE_INFORMATION) {
            val intent = Intent(context, ViewWordActivity::class.java)
            intent.putExtra("word", word)
            startActivity(intent)
        }else if(mode == MODE_CHOOSE){
            dictionaryViewModel.setSelected(word)
            itemClickListener?.dictionaryEntryClicked(word)
        }
    }

    fun getSelectedEntries(): List<DictionaryEntry>{
        return dictionaryViewModel.selectedEntries.toList()
    }

    companion object {
        val MODE_INFORMATION = 0
        val MODE_CHOOSE = 1
    }
}