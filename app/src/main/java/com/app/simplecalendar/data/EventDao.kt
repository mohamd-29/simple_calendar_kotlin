package com.app.simplecalendar.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.simplecalendar.model.Event


@Dao
interface EventDao {
    @Insert
    suspend fun insert(event: Event)
    @Delete
    suspend fun deleteInfo(event: Event)

    @Query("SELECT * FROM events ORDER BY date DESC")
    fun readAllData(): LiveData<List<Event>>
    @Query("SELECT COUNT(name) FROM events ")
    fun funGetLength(): LiveData<Int?>
    @Query("SELECT * FROM events")
    suspend fun getAll(): List<Event>

    @Query("SELECT * FROM events WHERE date = :date")
    suspend fun getEventsForDate(date: Long): List<Event>
}