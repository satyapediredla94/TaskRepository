package com.example.taskreminder.screens.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.R
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
            TopAppBar(title = {
                Text(
                    text = "Alarms",
                    color = Color.White,
                    fontFamily = FontFamily(
                        listOf(Font(R.font.poppins_regular))
                    )
                )
            },
                backgroundColor = Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        onPopBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                })
        },
        scaffoldState = scaffoldState,
        modifier = Modifier
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
                placeholder = {
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
                placeholder = {
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