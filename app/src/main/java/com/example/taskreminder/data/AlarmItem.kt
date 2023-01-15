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
    val repeat: RepeatInterval,
    val active: Boolean = false
) : Parcelable {

    /**
     * getTime() will return the exact time at which the the event
     * should be triggered
     */
    fun getTime(): Long {
        return Calendar.getInstance().timeInMillis + when (repeat.interval) {
            Interval.SECOND -> TimeUnit.SECONDS.toMillis(repeat.intervalTime.toLong())
            Interval.MINUTE -> TimeUnit.MINUTES.toMillis(repeat.intervalTime.toLong())
            Interval.HOUR -> TimeUnit.HOURS.toMillis(repeat.intervalTime.toLong())
            Interval.DAY -> TimeUnit.DAYS.toMillis(repeat.intervalTime.toLong())
        }
    }

}
