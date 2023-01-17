package com.example.taskreminder.data


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*
import java.util.concurrent.TimeUnit


@Entity
@Parcelize
data class AlarmItem(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val title: String,
    val message: String,
    val startHour: Int,
    val startMinute: Int,
    val startSeconds: Int = Calendar.getInstance().get(Calendar.SECOND),
    val repeat: RepeatInterval,
    val active: Boolean = false,
    val isInitial: Boolean = true
) : Parcelable {

    /**
     * getTime() will return the exact time at which the the event
     * should be triggered
     */
    fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            startHour,
            startMinute,
            startSeconds
        )
        return if (isInitial) calendar.timeInMillis else calendar.timeInMillis + when (repeat.interval) {
            Interval.SECOND -> TimeUnit.SECONDS.toMillis(repeat.intervalTime.toLong())
            Interval.MINUTE -> TimeUnit.MINUTES.toMillis(repeat.intervalTime.toLong())
            Interval.HOUR -> TimeUnit.HOURS.toMillis(repeat.intervalTime.toLong())
            Interval.DAY -> TimeUnit.DAYS.toMillis(repeat.intervalTime.toLong())
        }
    }

}
