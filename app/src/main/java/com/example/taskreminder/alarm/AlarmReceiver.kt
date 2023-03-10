package com.example.taskreminder.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.os.Build.VERSION
import androidx.core.app.NotificationCompat
import com.example.taskreminder.R
import com.example.taskreminder.ResultActivity
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.RepeatInterval
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.utils.NotificationConstants.CHANNEL_ID
import com.example.taskreminder.utils.goAsync
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmRepository: AlarmRepository

    @Inject
    lateinit var androidAlarmManager: AndroidAlarmManager

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var ringtone: Ringtone

    @Suppress("kotlin:S1874")
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
            Timber.e("Receiver triggered: $isSchedule")
            goAsync {
                alarmItem?.let {
                    showNotification(context, it)
                    val startHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                    val startMinute = Calendar.getInstance().get(Calendar.MINUTE)
                    if (isSchedule) {
                        alarmRepository.insertAlarmItem(
                            it.copy(
                                active = true,
                                repeat = RepeatInterval(
                                    it.repeat.intervalTime,
                                    it.repeat.interval
                                ),
                                startHour = startHour,
                                startMinute = startMinute,
                                startSeconds = Calendar.getInstance().get(Calendar.SECOND),
                                isInitial = false
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
            // Create an Intent for the activity you want to start
            val resultIntent = Intent(it, ResultActivity::class.java)
            // Create the TaskStackBuilder
            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                // Add the intent, which inflates the back stack
                addNextIntentWithParentStack(resultIntent)
                // Get the PendingIntent containing the entire back stack
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(alarmItem.title)
                .setContentText(alarmItem.message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()
            notificationManager.notify(alarmItem.id ?: alarmItem.hashCode(), notificationBuilder)
            playRingtone()
        }
    }

    private fun playRingtone() {
        try {
            ringtone.play()
        } catch (e: Exception) {
            e.printStackTrace()
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