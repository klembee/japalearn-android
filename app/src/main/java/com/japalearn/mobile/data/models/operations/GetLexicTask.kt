package com.japalearn.mobile.data.models.operations

import android.os.AsyncTask
import com.japalearn.mobile.data.models.learning.Vocab
import com.magestionplus.android.data.database.AppDatabase

class GetLexicTask(
    val database: AppDatabase,
    val callback: (List<Vocab>) -> Unit
): AsyncTask<String, Unit, List<Vocab>>() {

    override fun doInBackground(vararg word: String?): List<Vocab> {
        val list = ArrayList<Vocab>()

        if(word.isNotEmpty()) {
            word.forEach {
                it?.let {
                    list.add(database.lexicDao().retrieve(it))
                }
            }
        }else{
            //Retrieve all lexics
            list.addAll(database.lexicDao().retrieveAll())
        }

        return list.sortedBy {
            it.timeUntilNextReview()
        }
    }

    override fun onPostExecute(result: List<Vocab>) {
        super.onPostExecute(result)
        callback(result)
    }
}