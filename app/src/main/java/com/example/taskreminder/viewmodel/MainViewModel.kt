package com.example.taskreminder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.taskreminder.db.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    alarmRepository: AlarmRepository
) : ViewModel() {

    val alarms = alarmRepository.getAlarmItems()


}