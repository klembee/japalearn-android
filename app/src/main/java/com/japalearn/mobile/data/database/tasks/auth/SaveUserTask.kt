package com.japalearn.mobile.data.database.tasks.auth

import android.os.AsyncTask
import com.japalearn.mobile.data.models.User
import com.magestionplus.android.data.database.AppDatabase

/**
 * Taks to save the user in the database
 * @author Clement Bisaillon
 */
class SaveUserTask(
    val database: AppDatabase,
    val callback: () -> Unit
): AsyncTask<User, Unit, Unit>() {

    override fun doInBackground(vararg users: User?) {
        if(users.isNotEmpty()){
            users[0]?.let {
                database.userDao().set(it)
            }
        }
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        callback()
    }
}