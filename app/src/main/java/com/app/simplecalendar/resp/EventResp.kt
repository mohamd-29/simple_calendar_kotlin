package com.app.simplecalendar.resp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.app.simplecalendar.data.EventDao
import com.app.simplecalendar.model.Event



class EventResp(private val eventDao: EventDao) {
    val readAllData: LiveData<List<Event>> =eventDao.readAllData()
    val LatestLength:LiveData<Int?> =eventDao.funGetLength()
    suspend fun deleteInfo(event: Event) {
        eventDao.deleteInfo(event)
    }
    suspend fun addEvent(event: Event){
        eventDao.insert(event)
    }
}