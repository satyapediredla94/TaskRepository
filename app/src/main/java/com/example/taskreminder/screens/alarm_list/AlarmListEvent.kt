package com.example.taskreminder.screens.alarm_list

import com.example.taskreminder.data.AlarmItem

sealed class AlarmListEvent {
    data class OnDeleteAlarmItemClicked(val alarmItem: AlarmItem) : AlarmListEvent()
    data class OnDoneChanged(val alarmItem: AlarmItem, val active: Boolean): AlarmListEvent()
    object OnUndoDeleteClick : AlarmListEvent()
    data class OnAlarmItemClick(val alarmItem: AlarmItem) : AlarmListEvent()
    object OnAddAlarmItemClick: AlarmListEvent()
    data class OnActiveStatusChanged(val alarmItem: AlarmItem, val active: Boolean) : AlarmListEvent()
}