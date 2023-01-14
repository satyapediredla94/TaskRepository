package com.example.taskreminder

import com.example.taskreminder.utils.ScreenConstants

sealed class Screens(val name: String, val route: String) {
    class Home : Screens(ScreenConstants.HOME, ScreenConstants.HOME)
    class AddEditAlarm :
        Screens(ScreenConstants.ADD_EDIT_ALARM_NAME, ScreenConstants.ADD_EDIT_ALARM)
}