package com.example.taskreminder.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskreminder.db.AlarmRepository
import com.example.taskreminder.db.FakeRepository
import com.example.taskreminder.db.MockAlarmItem
import com.example.taskreminder.screens.alarm_list.AlarmListEvent
import com.example.taskreminder.utils.UIEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: AlarmRepository

    @ExperimentalCoroutinesApi
    @Before
    fun createRepository() {
        repository = FakeRepository()
        viewModel = MainViewModel(repository)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    @ExperimentalCoroutinesApi
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `test add alarm event trigger`() = runTest {
        viewModel.onEvent(AlarmListEvent.OnAddAlarmItemClick)
        val uiEvent = viewModel.uiEvent.first()
        assertNotNull(uiEvent)
        assertFalse(uiEvent is UIEvent.Navigate)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `test on item clicked event trigger`() = runTest {
        viewModel.onEvent(AlarmListEvent.OnAlarmItemClick(MockAlarmItem.activeInitialAlarmMinutes))
        val uiEvent = viewModel.uiEvent.first()
        assertNotNull(uiEvent)
        assertTrue(uiEvent is UIEvent.Navigate)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `test check alarms list from repository`() = runTest {
        val alarmList = listOf(
            MockAlarmItem.activeInitialAlarm,
            MockAlarmItem.inActiveInitialAlarm,
            MockAlarmItem.activeInitialAlarmSeconds
        )
        alarmList.forEach { repository.insertAlarmItem(it) }
        val alarms = viewModel.alarms.first()
        assertNotNull(alarms)
        assertEquals(alarms.count(), 3)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `test delete alarm from repository`() = runTest {
        val alarmList = listOf(
            MockAlarmItem.activeInitialAlarm,
            MockAlarmItem.inActiveInitialAlarm,
            MockAlarmItem.activeInitialAlarmSeconds
        )
        alarmList.forEach { repository.insertAlarmItem(it) }
        viewModel.onEvent(AlarmListEvent.OnDeleteAlarmItemClicked(alarmList[1]))
        val uiEvent = viewModel.uiEvent.first()
        assertNotNull(uiEvent is UIEvent.ShowSnackBar)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `test onActive StatusChanged from repository`() = runTest {
        val alarmList = listOf(
            MockAlarmItem.activeInitialAlarm,
            MockAlarmItem.inActiveInitialAlarm,
            MockAlarmItem.activeInitialAlarmSeconds
        )
        alarmList.forEach { repository.insertAlarmItem(it) }
        runBlocking {
            viewModel.onEvent(AlarmListEvent.OnActiveStatusChanged(alarmList[1], true))
        }
    }
}