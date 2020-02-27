package com.japalearn.mobile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.japalearn.mobile.R

class GrammarFragment : Fragment() {

    private lateinit var grammarViewModel: GrammarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        grammarViewModel =
            ViewModelProviders.of(this).get(GrammarViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_grammar, container, false)
        return root
    }
}