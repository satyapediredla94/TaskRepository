package com.example.taskreminder.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskreminder.data.AlarmItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarmItem(alarmItem: AlarmItem)

    @Delete
    suspend fun deleteAlarm(alarmItem: AlarmItem)

    @Query("SELECT * FROM AlarmItem")
    fun getAlarmItems(): Flow<List<AlarmItem>>

    @Query("SELECT * FROM AlarmItem WHERE id=:id")
    suspend fun getAlarmById(id: Int): AlarmItem

}