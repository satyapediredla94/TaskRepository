package com.example.taskreminder.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.taskreminder.ui.theme.textColorNight

@Composable
fun ShowDialog(
showPermissionDialog: (Boolean) -> Unit) {
    var openDialog by remember { mutableStateOf(true) }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            backgroundColor = textColorNight,
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        showPermissionDialog(true)
                    }
                ) {
                    Text(text = "OK", color = Color.Black)
                }
            },
            title = {
                Text(
                    text = "Alert",
                    fontSize = 20.sp
                )
            },
            text = {
                Text(
                    text = "Notification Permissions are needed for the app to properly remind. ",
                    fontSize = 16.sp
                )
            })
    }
}