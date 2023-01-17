package com.example.taskreminder.screens.add_edit

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.*

@Composable
fun ShowTimePicker(
    viewModel: AddEditAlarmViewModel = hiltViewModel()
) {
    val rightNow = Calendar.getInstance()
    val currentHour: Int =
        if (viewModel.startHour == 0) rightNow.get(Calendar.HOUR_OF_DAY) else viewModel.startHour
    val currentMinute: Int =
        if (viewModel.startMinute == 0) rightNow.get(Calendar.MINUTE) else viewModel.startMinute
    viewModel.onEvent(AddEditAlarmEvent.OnStartTimeChanged(currentHour, currentMinute))
    val timePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour: Int, minute: Int ->
            viewModel.onEvent(AddEditAlarmEvent.OnStartTimeChanged(hour, minute))
        }, currentHour, currentMinute, false
    )
    Button(
        onClick = {
            timePickerDialog.show()
        },
        modifier = Modifier.padding(0.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
    ) {
        Icon(
            imageVector = Icons.Default.Alarm,
            contentDescription = null,
            tint = Color.White
        )
    }
}