package com.japalearn.mobile.ui.report.reportbug

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.japalearn.mobile.utils.AppLocations

/**
 * View model for the report bug activity
 * @author Clement Bisaillon
 */
class ReportBugViewModel: ViewModel(), AdapterView.OnItemSelectedListener {

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> = _location

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    fun setDescription(description: String){
        _description.postValue(description)
    }

    /**
     * When nothing is selected
     */
    override fun onNothingSelected(view: AdapterView<*>?) {
        _location.postValue(AppLocations.LOCATION_DICTIONARY)
    }

    /**
     * When the user selects an item in the location spinner
     */
    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var location = AppLocations.LOCATION_DICTIONARY
        when(position){
            0 -> location = AppLocations.LOCATION_DICTIONARY
            1 -> location = AppLocations.LOCATION_FLASHCARDS
            2 -> location = AppLocations.LOCATION_STORIES
        }
        _location.postValue(location)
    }
}