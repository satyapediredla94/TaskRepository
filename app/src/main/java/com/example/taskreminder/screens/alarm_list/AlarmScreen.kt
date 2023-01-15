package com.example.taskreminder.screens.alarm_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.Interval
import com.example.taskreminder.data.RepeatInterval
import com.example.taskreminder.ui.theme.TaskReminderTheme

@Composable
fun AlarmItemComponent(
    alarmItem: AlarmItem,
    onEvent: (AlarmListEvent) -> Unit,
    navController: NavController = rememberNavController()
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                onEvent(AlarmListEvent.OnAlarmItemClick(alarmItem))
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = alarmItem.title, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = alarmItem.message, fontSize = 12.sp)
        }
        Row {
            Text(
                text = alarmItem.repeat.intervalTime.toString(),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = alarmItem.repeat.interval.name.lowercase(),
                fontSize = 16.sp
            )
        }
    }
}


@Preview
@Composable
private fun AlarmPreview() {
    TaskReminderTheme {
        Scaffold(content = {
            Box(modifier = Modifier.padding(it)) {
                AlarmItemComponent(
                    AlarmItem(
                        1,
                        "Title",
                        "Sample Message",
                        RepeatInterval(2, Interval.HOUR)
                    ),
                    onEvent = {}
                )
            }
        })

    }
}