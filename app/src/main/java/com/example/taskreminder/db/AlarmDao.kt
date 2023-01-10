package com.example.taskreminder.db

import androidx.room.*
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

    @Query("SELECT * FROM AlarmItem WHERE active = 1")
    fun getActiveAlarmItems(): Flow<List<AlarmItem>>

    @Query("SELECT * FROM AlarmItem WHERE id=:id")
    suspend fun getAlarmById(id: Int): AlarmItem

}