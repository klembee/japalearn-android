package com.japalearn.mobile.data.models.learning

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Keeps information about the lexic of a user
 * with its level
 */
@Entity(indices = [Index(value=["id"], unique = true), Index(value = ["word"], unique = true)])
data class Vocab (
    val word: String,
    val meanings: List<String>,
    val reading: String,
    val level: Int,
    val note: String,

    @SerializedName("required_level")
    val requiredLevel: Int,

    @SerializedName("last_time_studied")
    val lastTimeStudied: Long? = null,

    @SerializedName("audio_file_name")
    val audioFileName: String? = null,

    var synced: Boolean = false,
    var deleted: Boolean = false
): Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun needToReview(): Boolean{
        val now = System.currentTimeMillis()
        val timeSinceStudyInSeconds = (now - (lastTimeStudied ?: 0L)) / 1000

        if(level > TIMES.size) return (TIMES[TIMES.size - 1] - timeSinceStudyInSeconds) < 0
        return (TIMES[level] - timeSinceStudyInSeconds) < 0
    }

    fun timeUntilNextReview(): Long{
        val now = System.currentTimeMillis()
        val timeSinceStudyInSeconds = (now - (lastTimeStudied ?: 0L)) / 1000

        var timeToWaitSeconds: Long

        if(level > TIMES.size){
            timeToWaitSeconds = TIMES[TIMES.size - 1]
        }else{
            timeToWaitSeconds = TIMES[level]
        }
        if(timeToWaitSeconds - timeSinceStudyInSeconds <= 0){
            return 0L
        }

        return (timeToWaitSeconds - timeSinceStudyInSeconds) * 1000
    }

    fun showMeanings(): String{
        var meaning = ""
        var i = 1
        meanings.forEach {
            meaning += "$i. $it\n"
            i++
        }
        return meaning
    }

    companion object {
        //in seconds
        val TIMES = arrayOf<Long>(
            0,
            4 * 60 * 60, //4 heures
            8 * 60 * 60, //8 heures
            1 * 24 * 60 * 60, //1 day
            3 * 24 * 60 * 60, //3 days
            7 * 24 * 60 * 60, //5 days
            14 * 24 * 60 * 60, //15 days
            30 * 24 * 60 * 60, //30 days
            4 * 30 * 24 * 60 * 60 //4 months
        )
    }
}