package com.japalearn.mobile.ui.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.japalearn.mobile.MainActivity
import com.japalearn.mobile.R
import com.japalearn.mobile.data.Authenticator
import com.japalearn.mobile.data.repositories.AuthRepository

/**
 * Activity showing a form that allows the user to
 * log in
 * @author Clement Bisaillon
 */
class LoginActivity: AppCompatActivity() {

    private lateinit var authRepository: AuthRepository

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var createAccountBtn: Button
    private lateinit var loginBtn: Button

    private lateinit var accountManager: AccountManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        accountManager = AccountManager.get(this)
        authRepository = AuthRepository(this)

        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        createAccountBtn = findViewById(R.id.createAccountBtn)
        loginBtn = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener { login() }
        createAccountBtn.setOnClickListener { createAccount() }

        //Check if the user is already logged in
        //AuthRepository.checkIfLoggedIn(this, this::onLoginSuccess){}
    }

    /**
     * Go to the create account activity
     */
    private fun createAccount(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    /**
     * When the user clicks the login button
     */
    private fun login(){
        val email = emailField.text.toString()
        val password = passwordField.text.toString()

        val account = Account(email, Authenticator.ACCOUNT_TYPE)
        val token = authRepository.login(email, password)

        if(token != null && !TextUtils.isEmpty(token)){
            accountManager.addAccountExplicitly(account, password, null)
            accountManager.setAuthToken(account, Authenticator.AUTH_TOKEN_TYPE, token)
        }else{
            Log.e("LoginActivity", "Error logging in")
        }



        //AuthRepository.login(this, email, password, this::onLoginSuccess, this::onLoginFailed)
    }

    /**
     * If the log in succeed
     */
    private fun onLoginSuccess(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /**
     * If the login failed
     */
    private fun onLoginFailed(){
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
    }

    companion object{
        val KEY_ACCOUNT_NAME = "ACCOUNT_NAME"
    }
}