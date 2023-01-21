package com.example.taskreminder.screens.alarm_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.ui.theme.grey
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
    //This collects the events and observe only once
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
                        noOp()
                    }
                }
                else -> noOp()
            }
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        TopBar {}
        if (alarms.value.isNotEmpty()) {
            LazyColumn {
                items(alarms.value) {
                    AlarmItemComponent(alarmItem = it, onEvent = viewModel::onEvent)
                }
            }
        }
    }
    if (alarms.value.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    viewModel.onEvent(AlarmListEvent.OnAddAlarmItemClick)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AddAlarm, contentDescription = "Add Task",
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp),
                tint = grey
            )
            Text(
                text = "No Tasks Yet.",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}
