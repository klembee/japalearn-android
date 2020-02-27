package com.japalearn.mobile.data.datasources

import android.util.Log
import com.japalearn.mobile.data.api.ApiController
import com.japalearn.mobile.data.api.SuccessResponse
import com.japalearn.mobile.utils.ConnectionUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackDataSource(val authToken: String) {

    fun create(isBug: Boolean, section: String, description: String){
        ApiController.feedbackApi.createFeedback(authToken, isBug, section, description).enqueue( object: Callback<SuccessResponse>{
            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                Log.e("FeedbackDatasource", "Error while creating feedback: ${t.message}")
            }

            override fun onResponse(
                call: Call<SuccessResponse>,
                response: Response<SuccessResponse>
            ) {
                val body = response.body()
                if(body != null){
                    Log.i("ASDf", body.toString())
                }else{
                    Log.e("FeedbackDatasource", "Error while creating feedback: ${response}")
                }
            }
        })
    }
}