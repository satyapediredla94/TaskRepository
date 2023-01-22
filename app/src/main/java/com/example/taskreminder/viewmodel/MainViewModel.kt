package com.example.taskreminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.screens.Screens
import com.example.taskreminder.screens.alarm_list.AlarmListEvent
import com.example.taskreminder.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    val alarms = alarmRepository.getAlarmItems()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AlarmListEvent) {
        when (event) {
            is AlarmListEvent.OnDeleteAlarmItemClicked -> {
                viewModelScope.launch {
                    event.alarmItem.id?.let { alarmRepository.deleteAlarmById(it) }
                    sendUiEvent(UIEvent.ShowSnackBar("AlarmItem Deleted", "Undo"))
                }
            }
            AlarmListEvent.OnAddAlarmItemClick -> {
                sendUiEvent(UIEvent.Navigate(Screens.AddEditAlarm().route))
            }
            is AlarmListEvent.OnActiveStatusChanged -> {
                viewModelScope.launch {
                    alarmRepository.insertAlarmItem(
                        event.alarmItem.copy(
                            active = event.active
                        )
                    )
                }
            }
            is AlarmListEvent.OnAlarmItemClick -> {
                //Adding AlarmItem ID to get that AlarmItem based on ID
                sendUiEvent(
                    UIEvent.Navigate(Screens.AddEditAlarm().route + "?alarmId=${event.alarmItem.id}")
                )
            }
        }
    }

    private fun sendUiEvent(uiEvent: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

}