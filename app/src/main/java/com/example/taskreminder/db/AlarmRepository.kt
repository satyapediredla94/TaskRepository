package com.example.taskreminder.db

import com.example.taskreminder.data.AlarmItem
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun insertAlarmItem(alarmItem: AlarmItem)
    suspend fun deleteAlarm(alarmItem: AlarmItem)
    fun getAlarmItems(): Flow<List<AlarmItem>>
    fun getActiveAlarms(): Flow<List<AlarmItem>>
    suspend fun getAlarmById(id: Int): AlarmItem
    suspend fun deleteAlarmById(id: Int)
}