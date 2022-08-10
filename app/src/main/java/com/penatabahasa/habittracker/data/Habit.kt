package com.penatabahasa.habittracker.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "habits"
)
data class Habit(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "minutesFocus")
    val minutesFocus: Long,

    @ColumnInfo(name = "startTime")
    val startTime: String,

    @ColumnInfo(name = "priorityLevel")
    val priorityLevel: String
) : Parcelable
