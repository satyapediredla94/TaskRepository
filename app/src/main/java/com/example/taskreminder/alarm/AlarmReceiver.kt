package com.example.taskreminder.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.util.Log
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.utils.goAsync
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmRepository: AlarmRepository

    @Inject
    lateinit var androidAlarmManager: AndroidAlarmManager

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
            goAsync {
                alarmItem?.let {
                    if (isSchedule) {
                        alarmRepository.insertAlarmItem(it.copy(active = true))
                    } else {
                        alarmRepository.insertAlarmItem(it.copy(active = false))
                    }
                }
            }
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