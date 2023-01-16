package com.example.taskreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskreminder.screens.add_edit.AddEditAlarmScreen
import com.example.taskreminder.screens.alarm_list.MainAlarmsScreen
import com.example.taskreminder.ui.theme.TaskReminderTheme
import dagger.hilt.android.AndroidEntryPoint

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
                    val navController = rememberNavController()
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
                }
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