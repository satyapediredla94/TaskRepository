package com.example.taskreminder.screens.alarm_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.Interval
import com.example.taskreminder.data.RepeatInterval
import com.example.taskreminder.ui.theme.TaskReminderTheme
import com.example.taskreminder.ui.theme.textColorNight

@Composable
fun AlarmItemComponent(
    alarmItem: AlarmItem,
    onEvent: (AlarmListEvent) -> Unit
) {
    Card(
        elevation = 10.dp,
        backgroundColor = textColorNight,
        modifier = Modifier.padding(8.dp)
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
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Row {
                    Text(text = alarmItem.title, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "( Every ", modifier = Modifier.padding(top = 2.dp))
                    Row(Modifier.padding(top = 2.dp)) {
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
                    Text(modifier = Modifier.padding(top = 2.dp), text = " )")
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Switch(
                    modifier = Modifier
                        .align(Alignment.End),
                    checked = alarmItem.active,
                    onCheckedChange = {
                        onEvent(AlarmListEvent.OnActiveStatusChanged(alarmItem, it))
                    }
                )
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
                AlarmItemComponent(
                    AlarmItem(
                        1,
                        "Title",
                        "Sample Message",
                        1,
                        1,
                        repeat = RepeatInterval(2, Interval.HOUR),
                        active = true
                    ),
                    onEvent = {}
                )
            }
        })

    }
}