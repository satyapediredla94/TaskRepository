package com.example.taskreminder.db.typeConverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.taskreminder.data.RepeatInterval

@ProvidedTypeConverter
class IntervalTypeConverter(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun toIntervalJson(interval: RepeatInterval): String {
        return jsonParser.toJson(interval, RepeatInterval::class.java) ?: ""
    }

    @TypeConverter
    fun fromJsonToInterval(jsonString: String): RepeatInterval? {
        return jsonParser.fromJson<RepeatInterval>(jsonString, RepeatInterval::class.java)
    }

}