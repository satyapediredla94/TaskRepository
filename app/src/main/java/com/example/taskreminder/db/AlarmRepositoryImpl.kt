package com.example.taskreminder.db

import com.example.taskreminder.data.AlarmItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao
) : AlarmRepository {

    override suspend fun insertAlarmItem(alarmItem: AlarmItem) = alarmDao.insertAlarmItem(alarmItem)

    override suspend fun deleteAlarm(alarmItem: AlarmItem) = alarmDao.deleteAlarm(alarmItem)

    override fun getAlarmItems(): Flow<List<AlarmItem>> = alarmDao.getAlarmItems()

    override suspend fun getAlarmById(id: Int) = alarmDao.getAlarmById(id)

}