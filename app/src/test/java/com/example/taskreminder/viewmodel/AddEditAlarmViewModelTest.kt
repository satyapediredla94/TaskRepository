package com.example.taskreminder.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.taskreminder.data.AlarmItem
import com.example.taskreminder.data.Interval
import com.example.taskreminder.db.FakeRepository
import com.example.taskreminder.db.MockAlarmItem
import com.example.taskreminder.screens.add_edit.AddEditAlarmEvent
import com.example.taskreminder.utils.ArgumentConstants
import com.example.taskreminder.utils.UIEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class AddEditAlarmViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddEditAlarmViewModel
    private lateinit var repository: FakeRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var testDispatcher: TestDispatcher

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        repository = FakeRepository()
        viewModel = AddEditAlarmViewModel(repository, SavedStateHandle())
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    @ExperimentalCoroutinesApi
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `testing events to update alarm`() = runBlocking {
        viewModel.onEvent(AddEditAlarmEvent.OnTitleChange("Test 1"))
        viewModel.onEvent(AddEditAlarmEvent.OnMessageChange("Message 1"))
        viewModel.onEvent(AddEditAlarmEvent.OnStartTimeChanged(10, 0))
        viewModel.onEvent(AddEditAlarmEvent.OnTimeChange("10"))
        viewModel.onEvent(AddEditAlarmEvent.OnTimeIntervalChange(Interval.SECOND))
        viewModel.onEvent(AddEditAlarmEvent.OnSaveClicked)
        val alarmTitle = viewModel.title
        val alarmMessage = viewModel.message
        val startHour = viewModel.startHour
        val startMinute = viewModel.startMinute
        val time = viewModel.time
        val interval = viewModel.timeInterval
        assertEquals(alarmTitle, "Test 1")
        assertEquals(alarmMessage, "Message 1")
        assertEquals(startHour, 10)
        assertEquals(startMinute, 0)
        assertEquals(time, "10")
        assertEquals(interval, Interval.SECOND)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `testing events to when title is blank`() = runTest {
        viewModel.onEvent(AddEditAlarmEvent.OnTitleChange(""))
        viewModel.onEvent(AddEditAlarmEvent.OnSaveClicked)
        val uiEvent = viewModel.uiEvent.first()
        assertNotNull(uiEvent)
        assertTrue(uiEvent is UIEvent.ShowSnackBar)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `testing events to when message is blank`() = runTest {
        viewModel.onEvent(AddEditAlarmEvent.OnMessageChange(""))
        viewModel.onEvent(AddEditAlarmEvent.OnSaveClicked)
        val uiEvent = viewModel.uiEvent.first()
        assertNotNull(uiEvent)
        assertTrue(uiEvent is UIEvent.ShowSnackBar)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `testing events to when time is blank`() = runTest {
        viewModel.onEvent(AddEditAlarmEvent.OnTitleChange("Title 1"))
        viewModel.onEvent(AddEditAlarmEvent.OnTimeChange(""))
        viewModel.onEvent(AddEditAlarmEvent.OnSaveClicked)
        val uiEvent = viewModel.uiEvent.first()
        assertNotNull(uiEvent)
        assertTrue(uiEvent is UIEvent.ShowSnackBar)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `testing events to when time & title is not blank`() = runTest {
        val uiEvent = mutableListOf<UIEvent>()
        viewModel.onEvent(AddEditAlarmEvent.OnTitleChange("Title 1"))
        viewModel.onEvent(AddEditAlarmEvent.OnTimeChange("10"))
        viewModel.onEvent(AddEditAlarmEvent.OnSaveClicked)
        viewModel.viewModelScope.launch {
            viewModel.uiEvent.collect { value ->
                uiEvent.add(value)
            }
            assertTrue(uiEvent.isNotEmpty())
            assertTrue(uiEvent[0] is UIEvent.PopBackstack)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `testing saved state handle when alarm is not negative 1`() = runTest {
        var alarmItem: AlarmItem?
        val savedState = SavedStateHandle()
        savedState[ArgumentConstants.ALARM_ID] = 1
        repository.insertAlarmItem(MockAlarmItem.activeInitialAlarm)
        viewModel = AddEditAlarmViewModel(repository, savedState)
        delay(1000)
        runBlocking {
            alarmItem = viewModel.alarmItem
            assertNotNull(alarmItem)
            assertEquals(alarmItem?.id, 1)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `testing delete alarm`() = runTest {
        val savedState = SavedStateHandle()
        savedState[ArgumentConstants.ALARM_ID] = 1
        repository.insertAlarmItem(MockAlarmItem.activeInitialAlarm)
        viewModel = AddEditAlarmViewModel(repository, savedState)
        delay(1000)
        val alarmItem = viewModel.alarmItem
        assertNotNull(alarmItem)
        assertEquals(alarmItem?.id, 1)
        viewModel.onEvent(AddEditAlarmEvent.OnAlarmDeleted)
        delay(1000)
        assertTrue(repository.getAlarmItems().first().isEmpty())
    }
}