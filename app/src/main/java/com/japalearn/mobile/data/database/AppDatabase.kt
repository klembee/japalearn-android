package com.magestionplus.android.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.japalearn.mobile.data.database.converters.DateConverter
import com.japalearn.mobile.data.database.converters.StringListConverter
import com.japalearn.mobile.data.database.dao.*
import com.japalearn.mobile.data.models.*
import com.japalearn.mobile.data.models.learning.Kanji
import com.japalearn.mobile.data.models.learning.Radical
import com.japalearn.mobile.data.models.learning.Vocab

/**
 * The SQLite database containing cached and offline items
 * @author Clement Bisaillon
 */
@Database(entities = [User::class, FlashCardDeck::class, FlashCard::class, Kanji::class, Vocab::class, Radical::class], version = 16)
@TypeConverters(DateConverter::class, StringListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deckDao(): DeckDao
    abstract fun flashCardDao(): FlashCardDao
    abstract fun kanjiDao(): KanjiDao
    abstract fun vocabDao(): VocabDao
    abstract fun radicals(): RadicalDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{ instance = it }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context,
                AppDatabase::class.java, "japalearn.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration() //todo remove when publish
//                .fallbackToDestructiveMigrationOnDowngrade()
//                .addMigrations(
//                    Migration1to6()
//                )
                .build()
        }
    }
}