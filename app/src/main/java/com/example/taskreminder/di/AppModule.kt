package com.example.taskreminder.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import com.example.taskreminder.alarm.AndroidAlarmManager
import com.example.taskreminder.alarm.AndroidAlarmScheduler
import com.example.taskreminder.db.AlarmDatabase
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.db.AlarmRepositoryImpl
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
    fun provideAlarmRepo(
        alarmDb: AlarmDatabase
    ): AlarmRepository = AlarmRepositoryImpl(alarmDb.alarmDao())

    @Provides
    @Singleton
    fun providesAlarmScheduler(
        alarmManager: AlarmManager,
        @ApplicationContext context: Context
    ): AndroidAlarmManager = AndroidAlarmScheduler(context, alarmManager)

    @Provides
    @Singleton
    fun providesAndroidAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager = context.getSystemService(AlarmManager::class.java)

}