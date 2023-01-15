package com.example.taskreminder.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.taskreminder.data.AlarmItem
import java.util.Calendar
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
) : AndroidAlarmManager {

    companion object {
        const val ALARM_ITEM = "alarm_item"
        const val ALARM_BUNDLE = "alarm_bundle"
        const val IS_SCHEDULE = "is_schedule"
    }

    @SuppressLint("MissingPermission")
    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            val bundle = Bundle().apply {
                putParcelable(ALARM_ITEM, alarmItem)
                putBoolean(IS_SCHEDULE, true)
            }
            putExtra(ALARM_BUNDLE, bundle)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmItem.getTime(),
            PendingIntent.getBroadcast(
                context,
                alarmItem.id ?: alarmItem.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            val bundle = Bundle().apply {
                putParcelable(ALARM_ITEM, alarmItem)
                putBoolean(IS_SCHEDULE, false)
            }
            putExtra(ALARM_BUNDLE, bundle)
        }
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.id ?: alarmItem.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

}