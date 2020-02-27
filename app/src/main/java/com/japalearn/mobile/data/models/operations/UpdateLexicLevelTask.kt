package com.japalearn.mobile.data.models.operations

import android.accounts.Account
import android.os.AsyncTask
import com.japalearn.mobile.data.SyncAdapter
import com.japalearn.mobile.data.models.learning.Vocab
import com.magestionplus.android.data.database.AppDatabase

class UpdateLexicLevelTask(
    val account: Account?,
    val database: AppDatabase,
    val vocab: Vocab,
    val gotItRight: Boolean
): AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg p0: Unit?) {

        if(gotItRight){
            //Increase level
            if(vocab.level < 12) {
                database.lexicDao().increaseLevel(vocab.id)
            }
        }else{
            //Decrease level
            if(vocab.level > 1) {
                database.lexicDao().decreaseLevel(vocab.id)
            }else{
                //Increase to level 1 if it was at level 0 even if the user got it wrong
                database.lexicDao().increaseLevel(vocab.id)
            }
        }

        //Set the studied date to now
        database.lexicDao().setStudiedDate(vocab.id)
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        //Sync with the server
        SyncAdapter.requestSync(account)
    }
}