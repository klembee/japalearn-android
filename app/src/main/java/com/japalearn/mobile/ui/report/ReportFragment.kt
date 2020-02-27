package com.japalearn.mobile.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.japalearn.mobile.R

/**
 * A fragment displaying a form to report a bug
 * @author Clement Bisaillon
 */
class ReportFragment: Fragment() {

    private lateinit var reportViewModel: ReportViewModel
    private lateinit var reportTypeSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_bug_report, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Configure listeners when report type changes
        reportViewModel.reportTypeFragment.observe(this, Observer {
            this.reportTypeChanged(it)
        })

        reportTypeSpinner = view.findViewById(R.id.bug_report_type_spinner)
        configureTypeSpinner()
    }

    private fun reportTypeChanged(report: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.reportFormFragment, report)
        transaction?.commit()
    }

    /**
     * Configure the type spinner by giving it a listener and adapter
     */
    private fun configureTypeSpinner(){

        context?.let {
            val adapter = ArrayAdapter.createFromResource(it, R.array.activity_bug_report_type, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            reportTypeSpinner.adapter = adapter
            reportTypeSpinner.onItemSelectedListener = reportViewModel
        }

    }
}