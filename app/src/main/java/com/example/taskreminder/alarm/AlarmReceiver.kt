package com.example.taskreminder.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.taskreminder.R
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.RepeatInterval
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.utils.NotificationConstants.CHANNEL_ID
import com.example.taskreminder.utils.goAsync
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmReceiver"
    }

    @Inject
    lateinit var alarmRepository: AlarmRepository

    @Inject
    lateinit var androidAlarmManager: AndroidAlarmManager

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.e(intent?.action)
        if (isAfterReboot(intent)) {
            addAllAlarmsBack()
        } else {
            val bundle = intent?.getBundleExtra(AndroidAlarmScheduler.ALARM_BUNDLE)
            val alarmItem: AlarmItem? = if (VERSION.SDK_INT > 32) {
                bundle?.getParcelable(AndroidAlarmScheduler.ALARM_ITEM, AlarmItem::class.java)
            } else {
                bundle?.getParcelable(AndroidAlarmScheduler.ALARM_ITEM)
            }
            val isSchedule = bundle?.getBoolean(AndroidAlarmScheduler.IS_SCHEDULE) ?: false
            Log.e(TAG, "Receiver triggered: $isSchedule")
            goAsync {
                alarmItem?.let {
                    showNotification(context, it)
                    if (isSchedule) {
                        alarmRepository.insertAlarmItem(
                            it.copy(
                                active = true,
                                repeat = RepeatInterval(
                                    it.repeat.intervalTime,
                                    it.repeat.interval
                                )
                            )
                        )
                    } else {
                        alarmRepository.insertAlarmItem(it.copy(active = false))
                    }
                }
            }
        }
    }

    private fun showNotification(context: Context?, alarmItem: AlarmItem) {
        context?.let {
            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(alarmItem.title)
                .setContentText(alarmItem.message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(alarmItem.hashCode(), notificationBuilder)
        }
    }

    private fun addAllAlarmsBack() {
        goAsync {
            alarmRepository.getActiveAlarms().collect {
                it.forEach { alarmItem ->
                    androidAlarmManager.schedule(alarmItem)
                }
            }
        }
    }

    private fun isAfterReboot(intent: Intent?) = intent?.action == Intent.ACTION_BOOT_COMPLETED

}