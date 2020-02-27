package com.japalearn.mobile.data

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.japalearn.mobile.data.repositories.AuthRepository
import com.japalearn.mobile.ui.auth.LoginActivity

class Authenticator(val context: Context): AbstractAccountAuthenticator(context) {

    private val authRepository = AuthRepository(context)

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        val accountManager = AccountManager.get(context)
        var authToken = accountManager.peekAuthToken(account, authTokenType)

        //Retry login
        if(TextUtils.isEmpty(authToken)){
            val password = accountManager.getPassword(account)
            password?.let {
                account?.let {
                    authToken = authRepository.login(it.name, password)
                }
            }
        }

        if(authToken != null && !TextUtils.isEmpty(authToken)){
            account?.let {
                val result = Bundle()
                result.putString(AccountManager.KEY_ACCOUNT_NAME, it.name)
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, it.type)
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            }
        }

        //Couldnt get password, go to login activity
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(LoginActivity.KEY_ACCOUNT_NAME, account?.name)

        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        return ""
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        val intent = Intent(context, LoginActivity::class.java)

        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    companion object {
        val ACCOUNT_TYPE = "JAPALEARN.COM"
        val AUTH_TOKEN_TYPE = "full_access"
    }
}