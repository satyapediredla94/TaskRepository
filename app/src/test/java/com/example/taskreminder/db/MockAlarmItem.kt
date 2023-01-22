package com.example.taskreminder.db

import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.Interval
import com.example.taskreminder.data.RepeatInterval

object MockAlarmItem {

    val activeInitialAlarm = AlarmItem(
        1,
        "Test 1",
        "Message 1",
        10,
        0,
        0,
        RepeatInterval(10, Interval.HOUR),
        active = true,
        isInitial = true
    )

    val inActiveInitialAlarm = AlarmItem(
        2,
        "Test 1",
        "Message 1",
        10,
        0,
        0,
        RepeatInterval(10, Interval.HOUR),
        active = false,
        isInitial = true
    )

    val activeInitialAlarmMinutes = AlarmItem(
        3,
        "Test 1",
        "Message 1",
        10,
        0,
        0,
        RepeatInterval(10, Interval.MINUTE),
        active = true,
        isInitial = true
    )

    val activeInitialAlarmSeconds = AlarmItem(
        4,
        "Test 1",
        "Message 1",
        10,
        0,
        0,
        RepeatInterval(10, Interval.SECOND),
        active = true,
        isInitial = true
    )

    val activeNonInitialAlarmSeconds = AlarmItem(
        5,
        "Test 1",
        "Message 1",
        10,
        0,
        0,
        RepeatInterval(10, Interval.SECOND),
        active = true,
        isInitial = false
    )

    val activeNonInitialAlarmDay = AlarmItem(
        6,
        "Test 6",
        "Message 6",
        8,
        0,
        0,
        RepeatInterval(1, Interval.DAY),
        active = true,
        isInitial = false
    )

    val activeInitialAlarmDay = AlarmItem(
        7,
        "Test 7",
        "Message 7",
        9,
        0,
        0,
        RepeatInterval(10, Interval.DAY),
        active = true,
        isInitial = true
    )

    val activeNonInitialAlarmDay2 = AlarmItem(
        10,
        "Test 10",
        "Message 10",
        10,
        0,
        0,
        RepeatInterval(10, Interval.SECOND),
        active = true,
        isInitial = false
    )

}