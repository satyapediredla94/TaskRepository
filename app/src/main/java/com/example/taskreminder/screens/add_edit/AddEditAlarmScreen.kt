package com.example.taskreminder.screens.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.screens.alarm_list.TopBar
import com.example.taskreminder.utils.UIEvent

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
                        event.message,
                        event.action
                    )
                }
                else -> Unit
            }
        }
    })

    Scaffold(
        topBar = {
            TopBar(
                icon = Icons.Default.Close,
                onPopBackStack = onPopBackStack,
                fromList = false
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
                },
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Done",
                    tint = Color.White
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = { title ->
                    viewModel.onEvent(AddEditAlarmEvent.OnTitleChange(title))
                },
                label = {
                    Text(text = "Title")
                },
                modifier = Modifier
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
        }
    }
}

@Composable
@Preview
private fun Greeting() {
    AddEditAlarmScreen(onPopBackStack = { })
}