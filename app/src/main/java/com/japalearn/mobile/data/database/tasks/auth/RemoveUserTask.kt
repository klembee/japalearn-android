package com.japalearn.mobile.data.database.tasks.auth

import android.os.AsyncTask
import com.magestionplus.android.data.database.AppDatabase

/**
 * Log out the user by removing its information from the database
 * @author Clement Bisaillon
 */
class RemoveUserTask(
    val database: AppDatabase,
    val callback: () -> Unit
): AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg p0: Unit?) {
        database.userDao().remove()
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        callback()
    }
}