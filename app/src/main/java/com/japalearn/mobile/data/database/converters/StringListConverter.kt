package com.japalearn.mobile.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun fromStringList(json: String?): List<String>?{
        val listType = object : TypeToken<List<String>>() { }.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun stringListToJson(list: List<String>?): String?{
        return Gson().toJson(list)
    }
}