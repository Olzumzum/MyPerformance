package com.example.myperformance.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myperformance.dao.TimePerformDao
import com.example.myperformance.model.TimePerform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


/**
 * Connecting to a database using a room
 * Database name = "time_performe_database"
 * Filling in database data
 */
@Database(entities = arrayOf(TimePerform::class), version = 1, exportSchema = false)
abstract class TimePerformRoomDatabase : RoomDatabase() {
    abstract fun timePerformDao(): TimePerformDao

    class TimePerformDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val timePerformDao = database.timePerformDao()

                    timePerformDao.deleteAll()

                    fillDatabase(timePerformDao)
                }
            }
        }


        private suspend fun fillDatabase(timePerformDao: TimePerformDao){

            var valuePerform = TimePerform(
                    GregorianCalendar(2006, 5, 15).timeInMillis,
                    15)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 5, 16).timeInMillis,
                    4)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 18).timeInMillis,
                    0)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 19).timeInMillis,
                    3)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 20).timeInMillis,
                    5)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 21).timeInMillis,
                    4)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 22).timeInMillis,
                    8)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 1).timeInMillis,
                    6)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 24).timeInMillis,
                    11)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 25).timeInMillis,
                    11)
            timePerformDao.insert(valuePerform)

            valuePerform = TimePerform(
                    GregorianCalendar(2006, 0, 26).timeInMillis,
                    11)
            timePerformDao.insert(valuePerform)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TimePerformRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): TimePerformRoomDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimePerformRoomDatabase::class.java,
                        "tp_database"
                )
                        .addCallback(TimePerformDatabaseCallback(scope))
                        .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}