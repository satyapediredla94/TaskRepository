package com.example.taskreminder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.db.typeConverters.IntervalTypeConverter

@Database(entities = [AlarmItem::class], version = 1)
@TypeConverters(IntervalTypeConverter::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}