package com.example.taskreminder.db

import com.example.taskreminder.alarm.AndroidAlarmManager
import com.example.taskreminder.data.AlarmItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDao: AlarmDao,
    private val androidAlarmScheduler: AndroidAlarmManager
) : AlarmRepository {

    override suspend fun insertAlarmItem(alarmItem: AlarmItem) {
        alarmDao.insertAlarmItem(alarmItem)
        if (alarmItem.active) {
            androidAlarmScheduler.schedule(alarmItem)
        } else {
            androidAlarmScheduler.cancel(alarmItem)
        }
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) = alarmDao.deleteAlarm(alarmItem)

    override fun getAlarmItems(): Flow<List<AlarmItem>> = alarmDao.getAlarmItems()

    override fun getActiveAlarms() = alarmDao.getActiveAlarmItems()

    override suspend fun getAlarmById(id: Int) = alarmDao.getAlarmById(id)

}