package com.example.taskreminder.di

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.room.Room
import com.example.taskreminder.alarm.AndroidAlarmManager
import com.example.taskreminder.alarm.AndroidAlarmScheduler
import com.example.taskreminder.db.AlarmDao
import com.example.taskreminder.db.AlarmDatabase
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.db.AlarmRepositoryImpl
import com.example.taskreminder.utils.NotificationConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): AlarmDatabase = Room.databaseBuilder(context, AlarmDatabase::class.java, "AlarmDb").build()

    @Provides
    @Singleton
    fun providesAlarmScheduler(
        alarmManager: AlarmManager,
        @ApplicationContext context: Context
    ): AndroidAlarmManager = AndroidAlarmScheduler(context, alarmManager)

    @Provides
    @Singleton
    fun providesAlarmDao(
        alarmDb: AlarmDatabase
    ): AlarmDao = alarmDb.alarmDao()

    @Provides
    @Singleton
    fun provideAlarmRepo(
        alarmDao: AlarmDao,
        androidAlarmScheduler: AndroidAlarmManager
    ): AlarmRepository = AlarmRepositoryImpl(alarmDao, androidAlarmScheduler)

    @Provides
    @Singleton
    fun providesAndroidAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager = context.getSystemService(AlarmManager::class.java)

    @Provides
    @Singleton
    fun provideNotificationChannel(
        @ApplicationContext context: Context
    ): NotificationManager {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = NotificationConstants.CHANNEL_NAME
        val descriptionText = NotificationConstants.CHANNEL_DESC
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NotificationConstants.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

    @Provides
    @Singleton
    fun providesRingtoneManager(
        @ApplicationContext context: Context
    ): Ringtone {
        val path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        return RingtoneManager.getRingtone(
            context,
            path
        )
    }

}