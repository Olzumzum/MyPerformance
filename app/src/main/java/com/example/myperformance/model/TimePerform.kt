package com.example.myperformance.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Stores performance data
 * (date when the time and time value were considered)
 */
@Entity(tableName = "timeperform_table")
data class TimePerform(
        @ColumnInfo(name = "date_perf") val datePerform: Long,
        @ColumnInfo(name = "time_perf") val timePerf: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}