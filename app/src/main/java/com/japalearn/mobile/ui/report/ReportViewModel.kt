package com.japalearn.mobile.ui.report

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.ui.report.reportbug.ReportBugFragment
import com.japalearn.mobile.ui.report.suggestfeature.SuggestFeatureFragment

class ReportViewModel: ViewModel(), AdapterView.OnItemSelectedListener {

    private val bugFragment = ReportBugFragment()
    private val suggestionFragment = SuggestFeatureFragment()
    private val _reportTypeFragment = MutableLiveData<Fragment>()
    val reportTypeFragment = _reportTypeFragment


    /**
     * When nothing is selected from the type spinner
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        _reportTypeFragment.postValue(bugFragment)
    }

    /**
     * When the user selects report a bug of suggest a feature from the spinner
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when(pos){
            REPORT_BUG -> _reportTypeFragment.postValue(bugFragment)
            SUGGEST_FEATURE -> _reportTypeFragment.postValue(suggestionFragment)
        }
    }

    companion object{
        val REPORT_BUG = 0
        val SUGGEST_FEATURE = 1
    }
}