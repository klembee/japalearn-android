package com.japalearn.mobile.data.models.operations

import android.accounts.Account
import android.database.sqlite.SQLiteConstraintException
import android.os.AsyncTask
import android.util.Log
import com.japalearn.mobile.data.SyncAdapter
import com.japalearn.mobile.data.models.learning.Vocab
import com.magestionplus.android.data.database.AppDatabase

class AddToLexicTask(
    val database: AppDatabase,
    val account: Account?,
    val onPostExecuteCallback: (Boolean) -> Unit
): AsyncTask<Vocab, Unit, Boolean>() {

    override fun doInBackground(vararg vocabs: Vocab?): Boolean {
        if(vocabs.isEmpty()) return false
        vocabs.forEach {
            it?.let {
                try {
                    database.lexicDao().insert(it)
                }catch(exception: SQLiteConstraintException){
                    Log.i("AddToLexicTask","Didn't insert new lexic since it already existed")
                    return false
                }
            }
        }

        return true
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        onPostExecuteCallback(result ?: false)

        //Sync with the server
        SyncAdapter.requestSync(account)
    }

}