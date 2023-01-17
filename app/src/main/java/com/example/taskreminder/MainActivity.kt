package com.example.taskreminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskreminder.screens.ShowDialog
import com.example.taskreminder.screens.add_edit.AddEditAlarmScreen
import com.example.taskreminder.screens.alarm_list.MainAlarmsScreen
import com.example.taskreminder.ui.theme.TaskReminderTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskReminderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var isPermissionGranted by remember {
                        mutableStateOf(true)
                    }

                    var showPermissionDialog by remember {
                        mutableStateOf(true)
                    }
                    val navController = rememberNavController()
                    if (showPermissionDialog) {
                        CheckPermission { isGranted ->
                            isPermissionGranted = isGranted
                            showPermissionDialog = false
                        }
                    }
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home().route
                    ) {
                        composable(Screens.Home().route) {
                            MainAlarmsScreen {
                                navController.navigate(it.route)
                            }
                        }
                        composable(
                            Screens.AddEditAlarm().route + "?alarmId={alarmId}",
                            arguments = listOf(
                                navArgument(name = "alarmId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditAlarmScreen(onPopBackStack = {
                                navController.navigateUp()
                            })
                        }
                    }
                    if (!isPermissionGranted) {
                        ShowDialog {
                            showPermissionDialog = it
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CheckPermission(isGranted: (Boolean) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Timber.e("PERMISSION GRANTED")
            isGranted(true)
        } else {
            // Permission Denied: Do something
            Timber.e("PERMISSION DENIED")
            isGranted(false)
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val context = LocalContext.current
        // Check permission
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                // Some works that require permission
                Timber.d("Code requires permission")
                isGranted(true)
            }
            else -> {
                // Asking for permission
                SideEffect {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                isGranted(true)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskReminderTheme {
        Greeting("Android")
    }
}