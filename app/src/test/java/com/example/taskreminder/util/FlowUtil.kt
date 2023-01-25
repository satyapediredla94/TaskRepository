package com.example.taskreminder.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
fun <T> Flow<T>.getOrAwaitValue(
    time: Long = 2000L,
    afterCollect: () -> Unit = {}
): T? {
    var data: T? = null
    var isDataSet = false
    runBlocking {
        val collector = FlowCollector<T> { value ->
            data = value
            isDataSet = true
        }

        val job = async {
            collect(collector)
        }
        withTimeoutOrNull(time) {
            job.await()
        }
        afterCollect.invoke()
        if (!isDataSet) {
            throw TimeoutException("State Flow value was never set")
        }
        if (job.isActive) {
            job.cancel()
        }
    }
    return data
}