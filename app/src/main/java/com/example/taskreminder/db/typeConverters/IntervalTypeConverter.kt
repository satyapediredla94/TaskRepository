package com.example.taskreminder.db.typeConverters

import androidx.room.TypeConverter
import com.example.taskreminder.data.RepeatInterval
import com.google.gson.Gson

class IntervalTypeConverter {

    @TypeConverter
    fun toIntervalJson(interval: RepeatInterval): String {
        return Gson().toJson(interval, RepeatInterval::class.java) ?: ""
    }

    @TypeConverter
    fun fromJsonToInterval(jsonString: String): RepeatInterval? {
        return Gson().fromJson(jsonString, RepeatInterval::class.java)
    }

}