package com.japalearn.mobile.ui.report.reportbug

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
 * Fragment showing a form that lets the user describe the
 * bug he found
 * @author Clement Bisaillon
 */
class ReportBugFragment: Fragment() {
    private var feedbackRepository: FeedbackRepository? = null

    private lateinit var reportBugViewModel: ReportBugViewModel
    private lateinit var descriptionTxt: EditText
    private lateinit var reportLocationSpinner: Spinner
    private lateinit var submitBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportBugViewModel = ViewModelProviders.of(this).get(ReportBugViewModel::class.java)
        feedbackRepository = FeedbackRepository.instantiate(context)
        val root = inflater.inflate(R.layout.fragment_report_bug, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reportLocationSpinner = view.findViewById(R.id.report_location_spinner)

        submitBtn = view.findViewById(R.id.report_submit_btn)
        submitBtn.setOnClickListener { submit() }

        descriptionTxt = view.findViewById(R.id.report_description_txt)
        descriptionTxt.addTextChangedListener(object : TextChangedListener(){
            override fun afterTextChanged(editable: Editable?) {
                reportBugViewModel.setDescription(editable.toString())
            }
        })

        setupLocationSpinner()
    }


    private fun setupLocationSpinner(){
        context?.let {
            val adapter = ArrayAdapter.createFromResource(it, R.array.activity_bug_report_location, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            reportLocationSpinner.adapter = adapter
            reportLocationSpinner.onItemSelectedListener = reportBugViewModel
        }
    }

    /**
     * Submits the reports to the server
     */
    private fun submit(){
        val isBug = true
        val section = reportBugViewModel.location.value ?: ""
        val description = reportBugViewModel.description.value ?: ""

        feedbackRepository?.createFeedback(isBug, section, description)
        Toast.makeText(context, R.string.thank_you_for_feedback, Toast.LENGTH_SHORT).show()
        activity?.finish()
    }
}