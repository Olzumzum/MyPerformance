package com.example.myperformance.database

import android.content.Context
import android.util.Log

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myperformance.dao.TimePerformeDao
import com.example.myperformance.model.TimePerforme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


/**
 * Connecting to a database using a room
 * Database name = "time_performe_database"
 * Filling in database data
 */
@Database(entities = arrayOf(TimePerforme::class), version = 1, exportSchema = false)
abstract class TimePerformeRoomDatabase : RoomDatabase() {
    abstract fun timePerformeDao(): TimePerformeDao

    class TimePerformeDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val timePerformeDao = database.timePerformeDao()

                    timePerformeDao.deleteAll()

                    fillDatabase(timePerformeDao)
                }
            }
        }


        private suspend fun fillDatabase(timePerformeDao: TimePerformeDao){

            var valuePerforme = TimePerforme(
                    GregorianCalendar(2006, 5, 15).timeInMillis,
                    15)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(
                    GregorianCalendar(2006, 5, 16).timeInMillis,
                    4)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 18).timeInMillis, 0)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 19).timeInMillis, 3)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 20).timeInMillis, 5)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 21).timeInMillis, 4)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 22).timeInMillis, 8)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 1).timeInMillis, 6)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 24).timeInMillis, 11)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 25).timeInMillis, 11)
            timePerformeDao.insert(valuePerforme)

            valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 26).timeInMillis, 11)
            timePerformeDao.insert(valuePerforme)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TimePerformeRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): TimePerformeRoomDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimePerformeRoomDatabase::class.java,
                        "tp_database"
                )
                        .addCallback(TimePerformeDatabaseCallback(scope))
                        .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}