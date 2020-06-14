package com.example.myperformance.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Stores performance data
 * (date when the time and time value were considered)
 */
@Entity (tableName = "timeperforme_table")
class TimePerforme(
        @ColumnInfo(name = "date_perf") val datePerfor: Calendar,
        @ColumnInfo(name = "time_perf") val timePerf: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}