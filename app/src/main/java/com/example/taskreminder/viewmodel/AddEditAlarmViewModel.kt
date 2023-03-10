package com.example.taskreminder.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.Interval
import com.example.taskreminder.data.RepeatInterval
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.screens.add_edit.AddEditAlarmEvent
import com.example.taskreminder.utils.ArgumentConstants
import com.example.taskreminder.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditAlarmViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var alarmItem by mutableStateOf<AlarmItem?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var message by mutableStateOf("")
        private set

    var time by mutableStateOf("")
        private set

    var startHour by mutableStateOf(0)
        private set

    var startMinute by mutableStateOf(0)
        private set

    var timeInterval by mutableStateOf(Interval.SECOND)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val alarmId = savedStateHandle.get<Int>(ArgumentConstants.ALARM_ID)
        alarmId?.let {
            if (alarmId != -1) {
                viewModelScope.launch {
                    alarmRepository.getAlarmById(it).let { alarm ->
                        title = alarm.title
                        message = alarm.message
                        time = alarm.repeat.intervalTime.toString()
                        timeInterval = alarm.repeat.interval
                        this@AddEditAlarmViewModel.alarmItem = alarm
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditAlarmEvent) {
        when (event) {
            is AddEditAlarmEvent.OnMessageChange -> {
                message = event.description
            }
            is AddEditAlarmEvent.OnTimeChange -> {
                time = event.time
            }
            is AddEditAlarmEvent.OnTimeIntervalChange -> {
                timeInterval = event.timeInterval
            }
            is AddEditAlarmEvent.OnStartTimeChanged -> {
                startHour = event.startHour
                startMinute = event.startMinute
            }
            is AddEditAlarmEvent.OnAlarmDeleted -> {
                alarmItem?.let {
                    viewModelScope.launch {
                        alarmRepository.deleteAlarm(it)
                    }
                }
            }
            AddEditAlarmEvent.OnSaveClicked -> {
                if (title.isBlank()) {
                    sendUiEvent(UIEvent.ShowSnackBar("Title cannot be blank"))
                    return
                }
                if (time.isBlank()) {
                    sendUiEvent(UIEvent.ShowSnackBar("Time cannot be blank"))
                    return
                }
                viewModelScope.launch {
                    alarmRepository.insertAlarmItem(
                        AlarmItem(
                            title = title,
                            message = message,
                            active = alarmItem?.active ?: true,
                            id = alarmItem?.id,
                            startHour = startHour,
                            startMinute = startMinute,
                            startSeconds = 0,
                            repeat = RepeatInterval(time.toInt(), timeInterval)
                        )
                    )
                }
                sendUiEvent(UIEvent.PopBackstack)
            }
            is AddEditAlarmEvent.OnTitleChange -> {
                title = event.title
            }
        }
    }


    private fun sendUiEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}