package com.japalearn.mobile.data.repositories

import android.accounts.Account
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.japalearn.mobile.data.datasources.LexicDataSource
import com.japalearn.mobile.data.models.operations.AddToLexicTask
import com.japalearn.mobile.data.models.operations.GetLexicTask
import com.japalearn.mobile.data.models.learning.Vocab
import com.japalearn.mobile.data.models.operations.UpdateLexicLevelTask
import com.magestionplus.android.data.database.AppDatabase
import java.util.*

class LexicRepository(
    val context: Context,
    val localDatasource: AppDatabase,
    val account: Account?
) {
    private val cloudDataSource = LexicDataSource()

    /**
     * Sync the specified lexics with the cloud database
     * Don't call this on main thread
     */
    fun sync(authToken: String, vocabs: List<Vocab>){
        val preferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
        val lastSyncDate = preferences.getLong(KEY_LAST_LAST_SYNC, 0L)

        Log.i("ASDf", "${Date(lastSyncDate)}")


        //Send the unsynced local data to the cloud
        val newLexicsResult = cloudDataSource.sync(vocabs, authToken, lastSyncDate)
        Log.i("ASDF", "SYNCING: $newLexicsResult")
        if (newLexicsResult?.success == true) {
            Log.i("ASDF", "${newLexicsResult.models}")
            this.setSynced(vocabs)

            //update the lexics in the database
            newLexicsResult.models.forEach {
                localDatasource.lexicDao().insert(it)
            }

            //Set the last sync to now
            preferences.edit()
                .putLong(KEY_LAST_LAST_SYNC, System.currentTimeMillis())
                .apply()
        }
    }

    /**
     * Set the specified lexics as synced in the local database
     */
    private fun setSynced(vocabs: List<Vocab>){
        vocabs.forEach {
            if(it.deleted){
                localDatasource.lexicDao().delete(it)
            }else{
                localDatasource.lexicDao().setSynced(it.id)
            }
        }
    }

    fun addToLexic(vocabs: List<Vocab>, callback: (Boolean) -> Unit){
        AddToLexicTask(
            localDatasource,
            account,
            callback
        ).execute(*vocabs.toTypedArray())
    }

    /**
     * Updates the level of the lexic and sets the last studied date to current
     */
    fun updateLevel(vocab: Vocab, gotItRight: Boolean){
        UpdateLexicLevelTask(
            account,
            localDatasource,
            vocab,
            gotItRight
        ).execute()
    }

    fun getAllLexics(callback: (vocabs: List<Vocab>) -> Unit){
        GetLexicTask(
            localDatasource,
            callback
        ).execute()
    }

    fun getLexic(word: String, callback: (List<Vocab>) -> Unit){
        GetLexicTask(
            localDatasource,
            callback
        ).execute(word)
    }

    companion object{
        val KEY_LAST_LAST_SYNC = "LEXIC_LAST_SYNC"

        fun instantiate(context: Context?): LexicRepository?{
            context?.let {
                return LexicRepository(
                    it,
                    AppDatabase.invoke(it),
                    AuthRepository.getLoggedInUser(context)
                )
            }
            return null
        }
    }
}