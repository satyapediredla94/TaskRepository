package com.example.taskreminder.screens.alarm_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.Interval
import com.example.taskreminder.data.RepeatInterval
import com.example.taskreminder.ui.theme.TaskReminderTheme
import com.example.taskreminder.utils.UIEvent
import com.example.taskreminder.viewmodel.MainViewModel

@Composable
fun MainAlarmsScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigate: (UIEvent.Navigate) -> Unit
) {
    val noOp: () -> Unit = {}
    val scaffoldState = rememberScaffoldState()
    val alarms = viewModel.alarms.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> onNavigate(event)
                is UIEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(AlarmListEvent.OnUndoDeleteClick)
                    }
                }
                else -> noOp()
            }
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        TopBar(onEvent = viewModel::onEvent)
        LazyColumn {
            items(alarms.value) {
                AlarmItemComponent(alarmItem = it, onEvent = viewModel::onEvent)
            }
        }
    }
}


@Preview
@Composable
private fun AlarmPreview() {
    val alarmItem = listOf(
        AlarmItem(
            1,
            "Title",
            "Sample Message",
            RepeatInterval(2, Interval.HOUR)
        ),
        AlarmItem(
            1,
            "Title",
            "Sample Message",
            RepeatInterval(2, Interval.HOUR)
        ),
        AlarmItem(
            1,
            "Title",
            "Sample Message",
            RepeatInterval(2, Interval.HOUR)
        ),
        AlarmItem(
            1,
            "Title",
            "Sample Message",
            RepeatInterval(2, Interval.HOUR)
        )
    )
    TaskReminderTheme {
        Scaffold(content = {
            Box(modifier = Modifier.padding(it)) {
                MainAlarmsScreen() {}
            }
        })

    }
}
