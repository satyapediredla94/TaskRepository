package com.example.taskreminder.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class RepeatInterval(
    val intervalTime: Int, val interval: Interval
) : Parcelable

