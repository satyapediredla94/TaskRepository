package com.example.taskreminder.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.Interval
import com.example.taskreminder.data.RepeatInterval
import com.example.taskreminder.ui.theme.TaskReminderTheme

@Composable
fun MainAlarmsScreen(
    alarmItem: List<AlarmItem>,
    navController: NavController = rememberNavController()
) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopBar()
        LazyColumn {
            items(alarmItem) {
                AlarmItemComponent(alarmItem = it)
            }
        }
    }
}


@Preview
@Composable
private fun AlarmPreview() {
    TaskReminderTheme {
        Scaffold(content = {
            Box(modifier = Modifier.padding(it)) {
                MainAlarmsScreen(
                    alarmItem = listOf(
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
                )
            }
        })

    }
}
