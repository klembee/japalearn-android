package com.japalearn.mobile.data

import android.accounts.Account
import android.accounts.AccountManager
import android.content.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.japalearn.mobile.data.datasources.LexicDataSource
import com.japalearn.mobile.data.repositories.LexicRepository
import com.japalearn.mobile.ui.auth.LoginActivity
import com.magestionplus.android.data.database.AppDatabase

class SyncAdapter @JvmOverloads constructor(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean = false, val contentResolver: ContentResolver = context.contentResolver): AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {

    private val database = AppDatabase.invoke(context)
    private val lexicRepository: LexicRepository = LexicRepository.instantiate(context)!!

    override fun onPerformSync(
        account: Account?,
        extras: Bundle?,
        authority: String?,
        provider: ContentProviderClient?,
        syncResult: SyncResult?
    ) {
        val authToken = AccountManager.get(context).blockingGetAuthToken(account, Authenticator.AUTH_TOKEN_TYPE, false);
        syncLexic(authToken)
    }

    /**
     * Sync the lexics
     */
    private fun syncLexic(authToken: String){
        val lexicsToSync = database.vocabDao().getUnsynced()
        lexicRepository.sync(authToken, lexicsToSync)
    }

    companion object {
        fun requestSync(account: Account?){
            account?.let {
                val settings = Bundle().apply {
                    putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
                    putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true)
                }
                ContentResolver.requestSync(account, "com.japalearn", settings)
            }
        }
    }
}