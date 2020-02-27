package com.japalearn.mobile.data.repositories

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.StrictMode
import com.japalearn.mobile.data.Authenticator
import com.japalearn.mobile.data.api.auth.AuthDatasource
import com.japalearn.mobile.data.database.tasks.auth.RemoveUserTask
import com.japalearn.mobile.data.database.tasks.auth.SaveUserTask
import com.japalearn.mobile.data.models.User
import com.magestionplus.android.data.database.AppDatabase


/**
 * Repository that handles authentication
 * @author Clement Bisaillon
 */
class AuthRepository(val context: Context) {

    private val accountManager = AccountManager.get(context)
    private val authDataSource: AuthDatasource = AuthDatasource()

    init {
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
    }

    /**
     * Get the currently logged in user
     * @return the logged in user or null if user is not logged in
     */
    fun get(): Account? {
        if(accountManager.accounts.isEmpty()) return null
        return accountManager.accounts[0]
    }

    /**
     * Check if the user is still logged in. If not, remove it from the database
     */
    fun checkIfLoggedIn(onSuccess: () -> Unit = {}, onFailed: () -> Unit){
        val user = get()
        if(user != null){
            //todo
//            token = accountManager.getAuthToken(get(), "full_access", )
//            authDataSource.verifyLoggedIn(user., onSuccess){
//                logout(onFailed)
//            }
        }else{
            logout(onFailed)
        }
    }

    /**
     * Login the the API
     * @return the auth token
     */
    fun login(email: String, password: String): String?{
        return authDataSource.login(email, password)
    }

    /**
     * Log out from the application
     */
    fun logout(callback: () -> Unit){
        RemoveUserTask(AppDatabase.invoke(context), callback).execute()
    }

    /**
     * Register the user
     */
    fun register(email: String, name: String, password: String, onSuccess: () -> Unit, onFailed: () -> Unit){
        authDataSource.register(email, name, password){
            if(it.success){
                it.user?.let {
                    saveUserToLocalDatabase(it, onSuccess)
                }
            }else{
                onFailed()
            }
        }
    }

    /**
     * Remember the user by saving its data in the local database
     */
    private fun saveUserToLocalDatabase(user: User, callback: () -> Unit){
        SaveUserTask(AppDatabase.invoke(context), callback).execute(user)
    }

    companion object {
        fun getLoggedInUser(context: Context): Account?{
            val accounts = AccountManager.get(context).getAccountsByType(Authenticator.ACCOUNT_TYPE)
            if(accounts.isEmpty()) return null
            return accounts[0]
        }
    }

}