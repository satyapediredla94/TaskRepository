package com.example.taskreminder.screens.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.screens.alarm_list.TopBar
import com.example.taskreminder.utils.UIEvent
import com.example.taskreminder.utils.formatTwo
import com.example.taskreminder.viewmodel.AddEditAlarmViewModel

@Composable
fun AddEditAlarmScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditAlarmViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true, block = {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UIEvent.PopBackstack -> onPopBackStack()
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message, event.action
                    )
                }
                else -> Unit
            }
        }
    })

    Scaffold(topBar = {
        TopBar(
            icon = Icons.Default.Close, onPopBackStack = onPopBackStack, fromList = false
        )
    },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditAlarmEvent.OnSaveClicked)
                }, backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done",
                    tint = Color.White
                )
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(value = viewModel.title, onValueChange = { title ->
                viewModel.onEvent(AddEditAlarmEvent.OnTitleChange(title))
            }, label = {
                Text(text = "Title")
            }, modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.message,
                onValueChange = { description ->
                    viewModel.onEvent(AddEditAlarmEvent.OnMessageChange(description))
                },
                label = {
                    Text(text = "Message")
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            IntervalTimeComponent()
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Next Alarm: ", Modifier.padding(top = 12.dp), fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    Text(
                        text = "${(viewModel.startHour).formatTwo()}:${(viewModel.startMinute).formatTwo()}",
                        Modifier.padding(top = 12.dp),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ShowTimePicker()
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            viewModel.alarmItem?.let {
                Button(
                    onClick = {
                        viewModel.onEvent(AddEditAlarmEvent.OnAlarmDeleted)
                        onPopBackStack()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                    modifier = Modifier.align(Alignment.End),
                    contentPadding = PaddingValues(horizontal = 32.dp)
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            }
        }
    }
}

@Composable
@Preview
private fun Greeting() {
    AddEditAlarmScreen(onPopBackStack = { })
}