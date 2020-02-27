package com.japalearn.mobile.ui.report.suggestfeature

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.japalearn.mobile.R
import com.japalearn.mobile.data.repositories.FeedbackRepository
import com.japalearn.mobile.utils.TextChangedListener

/**
 * Fragment showing a form that lets the user explain the feature
 * he would like to have added to the application
 * @author Clement Bisaillon
 */
class SuggestFeatureFragment: Fragment() {
    private var feedbackRepository: FeedbackRepository? = null
    private lateinit var suggestFeatureViewModel: SuggestFeatureViewModel
    private lateinit var descriptionTxt: EditText
    private lateinit var reportLocationSpinner: Spinner
    private lateinit var submitBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        suggestFeatureViewModel = ViewModelProviders.of(this).get(SuggestFeatureViewModel::class.java)
        feedbackRepository = FeedbackRepository.instantiate(context)
        val root = inflater.inflate(R.layout.fragment_suggest_feature, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        descriptionTxt = view.findViewById(R.id.report_description_txt)
        descriptionTxt.addTextChangedListener(object : TextChangedListener(){
            override fun afterTextChanged(editable: Editable?) {
                suggestFeatureViewModel.setDescription(editable.toString())
            }
        })

        reportLocationSpinner = view.findViewById(R.id.report_location_spinner)
        setupLocationSpinner()

        submitBtn = view.findViewById(R.id.report_submit_btn)
        submitBtn.setOnClickListener { submit() }
    }

    private fun setupLocationSpinner(){
        context?.let {
            val adapter = ArrayAdapter.createFromResource(it, R.array.activity_bug_report_location, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            reportLocationSpinner.adapter = adapter
            reportLocationSpinner.onItemSelectedListener = suggestFeatureViewModel
        }
    }

    /**
     * Submits the reports to the server
     */
    private fun submit(){
        val isBug = false
        val section = suggestFeatureViewModel.location.value ?: ""
        val description = suggestFeatureViewModel.description.value ?: ""

        feedbackRepository?.createFeedback(isBug, section, description)
        //todo: go back to main activity Show thank you
        Toast.makeText(context, R.string.thank_you_for_feedback, Toast.LENGTH_SHORT).show()
        activity?.finish()
    }
}