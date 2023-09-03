package com.app.simplecalendar.data

import android.content.Context



import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.simplecalendar.model.Event


@Database(entities = [Event::class], version = 9)
    abstract class EventDatabase : RoomDatabase() {
        abstract fun eventDao(): EventDao

        companion object {
            private var instance: EventDatabase? = null

            fun getDatabase(context: Context): EventDatabase {
                if (instance == null) {
                    synchronized(EventDatabase::class) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            EventDatabase::class.java,
                            "events_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
                return instance!!
            }
        }

    }
