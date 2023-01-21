package com.example.taskreminder.screens.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskreminder.data.Interval
import com.example.taskreminder.viewmodel.AddEditAlarmViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IntervalTimeComponent(
    viewModel: AddEditAlarmViewModel = hiltViewModel()
) {
    val intervalList = arrayOf(
        Interval.SECOND,
        Interval.MINUTE,
        Interval.HOUR,
        Interval.DAY,
    )
    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        TextField(
            label = { Text(text = "Time") },
            value = viewModel.time,
            onValueChange = { time ->
                viewModel.onEvent(AddEditAlarmEvent.OnTimeChange(time))
            },
            modifier = Modifier.fillMaxWidth(0.4f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.width(4.dp))
        ExposedDropdownMenuBox(
            expanded = expanded, onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            // text field
            TextField(
                value = viewModel.timeInterval.name,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Time Interval") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            //Menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                intervalList.forEachIndexed { _, itemValue ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            viewModel.onEvent(AddEditAlarmEvent.OnTimeIntervalChange(itemValue))
                        },
                        enabled = true
                    ) {
                        Text(text = itemValue.name)
                    }
                }
            }
        }
    }
}