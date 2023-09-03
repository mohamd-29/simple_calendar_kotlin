package com.app.simplecalendar.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date :Long,
    val stamp :String ,
    val name: String,
    val time: String
)