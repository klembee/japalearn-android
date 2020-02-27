package com.japalearn.mobile.data.repositories

import android.content.Context
import com.japalearn.mobile.data.datasources.FeedbackDataSource
import com.japalearn.mobile.utils.ConnectionUtility

class FeedbackRepository(
    private val context: Context,
    private val cloudDataSource: FeedbackDataSource
) {
    fun createFeedback(isBug: Boolean, section: String, description: String){
        if(ConnectionUtility.isConnected(context)) {
            cloudDataSource.create(isBug, section, description)
        }else{
            //todo: Save to database with synced = false
        }
    }

    companion object{
        fun instantiate(context: Context?): FeedbackRepository?{
            context?.let {
                //todo: authentication
                val authToken = ""
                return FeedbackRepository(context, FeedbackDataSource(authToken))
            }
            return null
        }
    }
}