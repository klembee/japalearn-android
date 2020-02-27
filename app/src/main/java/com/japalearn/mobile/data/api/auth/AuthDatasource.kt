package com.japalearn.mobile.data.api.auth

import android.util.Log
import com.japalearn.mobile.data.api.ApiController
import com.japalearn.mobile.data.api.SuccessResponse
import com.japalearn.mobile.data.api.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthDatasource {

    /**
     * Login the user to the API
     */
    fun login(email: String, password: String): String?{
        val response = ApiController.authApi.login(email, password).execute()
        response.body()?.let {
            if(it.success){
                if(it.user != null){
                    return it.user.authToken
                }else{
                    return null
                }
            }else{
                return null
            }
        }
        return null
    }

    /**
     * Register a new account to the application
     */
    fun register(email: String, name: String, password: String, callback: (response: UserResponse) -> Unit){
        ApiController.authApi.register(email, name, password).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("AuthDataSource", "Error while registering: ${t.message}")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val body = response.body()
                if(body != null){
                    callback(body)
                }else{
                    Log.e("AuthDataSource", "Error while registering: ${response}")
                }
            }
        })
    }

    /**
     * Verify that the user is still logged in
     */
    fun verifyLoggedIn(authToken: String, onSuccess: () -> Unit = {}, onFailed: () -> Unit){
        ApiController.authApi.verify("Bearer $authToken").enqueue(object: Callback<SuccessResponse>{
            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                Log.e("AuthDataSource", "Failed to check that the user was still logged in")
                onFailed()
            }

            override fun onResponse(
                call: Call<SuccessResponse>,
                response: Response<SuccessResponse>
            ) {
                val body = response.body()
                if(body != null){
                    if(body.success){
                        onSuccess()
                    }else{
                        onFailed()
                    }
                }else{
                    Log.e("AuthDataSource", "Failed to check that the user was still logged in")
                    onFailed()
                }
            }
        })
    }
}