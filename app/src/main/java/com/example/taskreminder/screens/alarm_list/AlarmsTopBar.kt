package com.example.taskreminder.screens.alarm_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.ui.theme.Shapes
import com.example.taskreminder.ui.theme.TaskReminderTheme
import com.example.taskreminder.viewmodel.MainViewModel


@Composable
fun TopBar(
    viewModel: MainViewModel = hiltViewModel(),
    icon: ImageVector = Icons.Default.Add,
    fromList: Boolean = true,
    showTopFAB: Boolean = true,
    onPopBackStack: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = "Task Reminder",
            fontSize = 24.sp
        )
        if (showTopFAB) {
            IconButton(
                onClick = {
                    if (fromList) {
                        viewModel.onEvent(AlarmListEvent.OnAddAlarmItemClick)
                    } else {
                        onPopBackStack()
                    }
                },
                modifier = Modifier
                    .clip(Shapes.large)
                    .background(Color.Black)
            ) {
                Icon(
                    imageVector = icon, contentDescription = "Add an Alarm",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TaskReminderTheme {
        Scaffold(content = {
            Box(modifier = Modifier.padding(it)) {
                TopBar {}
            }
        })
    }
}