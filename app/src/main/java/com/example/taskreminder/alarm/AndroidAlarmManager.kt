package com.example.taskreminder.alarm

import com.example.taskreminder.data.AlarmItem

interface AndroidAlarmManager {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}