package com.example.myperformance.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myperformance.dao.TimePerformeDao
import com.example.myperformance.model.TimePerforme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



/**
 * Connecting to a database using a room
 * Database name = "time_performe_database"
 * Filling in database data
 */
@Database(entities = arrayOf(TimePerforme::class), version = 1, exportSchema = false)
abstract class TPerfRoomDatabase : RoomDatabase() {
    abstract fun timePerformeDao(): TimePerformeDao

    class TPerfDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val timePerformeDao = database.timePerformeDao()

                    timePerformeDao.deleteAll()
//                    var valuePerforme = TimePerforme(GregorianCalendar(2006, 5, 15), 15)
//                    timePerformeDao.insert(valuePerforme)
//
//                    valuePerforme = TimePerforme(GregorianCalendar(2006, 5, 16), 4)
//                    timePerformeDao.insert(valuePerforme)
//
//                    valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 18), 0)
//                    timePerformeDao.insert(valuePerforme)
//
//                    valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 19), 3)
//                    timePerformeDao.insert(valuePerforme)
//
//                    valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 20), 3)
//                    timePerformeDao.insert(valuePerforme)
//
//                    valuePerforme = TimePerforme(GregorianCalendar(2006, 0, 21), 3)
//                    timePerformeDao.insert(valuePerforme)


                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TPerfRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): TPerfRoomDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TPerfRoomDatabase::class.java,
                        "tp_database"
                )
                        .addCallback(TPerfDatabaseCallback(scope))
                        .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}