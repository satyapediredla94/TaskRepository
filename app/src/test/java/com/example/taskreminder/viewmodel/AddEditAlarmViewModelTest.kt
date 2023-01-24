package com.example.taskreminder.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.taskreminder.data.Interval
import com.example.taskreminder.db.FakeRepository
import com.example.taskreminder.screens.add_edit.AddEditAlarmEvent
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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class AddEditAlarmViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AddEditAlarmViewModel
    private lateinit var repository: FakeRepository

    @Before
    @ExperimentalCoroutinesApi
    fun setUp() {
        repository = FakeRepository()
        viewModel = AddEditAlarmViewModel(repository, SavedStateHandle())
        Dispatchers.setMain(StandardTestDispatcher())
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
        viewModel.onEvent(AddEditAlarmEvent.OnTitleChange("Title 1"))
        viewModel.onEvent(AddEditAlarmEvent.OnTimeChange("10"))
        viewModel.onEvent(AddEditAlarmEvent.OnSaveClicked)
    }
}