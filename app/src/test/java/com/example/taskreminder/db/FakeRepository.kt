package com.example.taskreminder.db

import com.example.taskreminder.data.AlarmItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository: AlarmRepository {

    private val alarmList: ArrayList<AlarmItem> = arrayListOf()

    override suspend fun insertAlarmItem(alarmItem: AlarmItem) {
        alarmList.add(alarmItem)
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        alarmList.remove(alarmItem)
    }

    override fun getAlarmItems(): Flow<List<AlarmItem>> = flow {
        emit(alarmList)
    }

    override fun getActiveAlarms(): Flow<List<AlarmItem>> = flow {
        alarmList.filter { it.active }
    }

    override suspend fun getAlarmById(id: Int): AlarmItem = alarmList.first { it.id == id }

    override suspend fun deleteAlarmById(id: Int) {
        val alarmItem = getAlarmById(id)
        deleteAlarm(alarmItem)
    }
}