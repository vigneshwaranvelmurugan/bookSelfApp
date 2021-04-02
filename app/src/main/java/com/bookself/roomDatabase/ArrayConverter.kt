package com.bookself.roomDatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArrayConverter {

    @TypeConverter
    fun fromStringArray(value: ArrayList<String>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringArray(value: String): ArrayList<String> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(value, type)
    }

}