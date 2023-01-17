package com.example.taskreminder.screens.add_edit

import com.example.taskreminder.data.Interval

sealed class AddEditAlarmEvent {
    data class OnTitleChange(val title: String) : AddEditAlarmEvent()
    data class OnMessageChange(val description: String) : AddEditAlarmEvent()
    data class OnTimeChange(val time: String) : AddEditAlarmEvent()
    data class OnTimeIntervalChange(val timeInterval: Interval) : AddEditAlarmEvent()
    object OnAlarmDeleted : AddEditAlarmEvent()
    object OnSaveClicked : AddEditAlarmEvent()
    data class OnStartTimeChanged(val startHour: Int, val startMinute: Int): AddEditAlarmEvent()
}