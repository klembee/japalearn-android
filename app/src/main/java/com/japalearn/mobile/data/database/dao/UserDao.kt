package com.japalearn.mobile.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.japalearn.mobile.data.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun get(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(user: User)

    @Query("DELETE FROM user")
    fun remove()
}