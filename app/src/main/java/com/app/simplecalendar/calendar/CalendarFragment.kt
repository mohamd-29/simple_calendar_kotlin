package com.app.simplecalendar.calendar

import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.simplecalendar.MainActivity
import com.app.simplecalendar.R
import com.app.simplecalendar.data.EventDatabase
import com.app.simplecalendar.databinding.FragmentCalendarBinding
import com.app.simplecalendar.model.Event
import com.app.simplecalendar.viewmodel.EventViewModel

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {
    private lateinit var mEventViewModel: EventViewModel
    private lateinit var name2: String
    private lateinit var time2: String
    val time = ""
    lateinit var  selectedTime :String
    private lateinit var calendarView: MaterialCalendarView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCalendarBinding>(
            inflater,
          R.layout.fragment_calendar, container, false
        )
        mEventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        val adapter =Radapter(mEventViewModel)
        mEventViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { event ->
            adapter.setData(event)
        })
        mEventViewModel.latestLength.observe(viewLifecycleOwner, androidx.lifecycle.Observer { inf ->
            if (inf == 0) {
                binding.textView19.visibility = View.VISIBLE


            } else {
                binding.textView19.visibility = View.GONE

            }
        })

        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, true)
        adapter.notifyDataSetChanged()
        calendarView = binding.calendarView

        calendarView.selectionColor=(ContextCompat.getColor(this.requireContext(), R.color.n4))
        calendarView.arrowColor=(ContextCompat.getColor(this.requireContext(), R.color.b))
        calendarView.setOnDateChangedListener { widget, date, selected ->
            showAddEventDialog(date)
        }
        GlobalScope.launch {
            val events = EventDatabase.getDatabase(requireContext()).eventDao().getAll()
            events.forEach { event ->
                val date = CalendarDay.from(Date(event.date))
                val eventDecorator = EventDecorator(Color.RED, setOf(date), event.name,event.time)
                calendarView.addDecorator(eventDecorator)

            }

        }
        calendarView.setOnDateChangedListener { widget, date, selected ->
            showAddEventDialog(date)
        }


        return binding.root


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showAddEventDialog(date: CalendarDay) {
        val dialog = AlertDialog.Builder(this.context, R.style.RoundedDialog)
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_event, null)
        dialog.setView(dialogView)

        dialog.setCancelable(false)


        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)

        val hour = timePicker.hour
        val minute = timePicker.minute
        val amPm = if (timePicker.hour >= 12) {
            "PM"
        } else {
            "AM"
        }

        selectedTime = " $hour:$minute  " + amPm
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            selectedTime = "$hourOfDay:$minute"
            val hour = timePicker.hour
            val minute = timePicker.minute
            val amPm = if (timePicker.hour >= 12) {
                "PM"
            } else {
                "AM"
            }

            selectedTime = " $hour:$minute  " + amPm
            time2 = "$hour:$minute"
        }



        name2 = dialogView.findViewById<EditText>(R.id.et_event_name).text.toString()

        val eventName = dialogView.findViewById<EditText>(R.id.et_event_name).text

        dialog.setPositiveButton("Add") { _, _ ->
            if (eventName.toString()=="")
            {
                Toast.makeText(this.requireContext(), "please enter event name ",Toast.LENGTH_LONG).show()

            }
            else {
                // Save the event to the database or add it to the calendar view
                addEventToCalendar(date, eventName, selectedTime)
            }


        }
        dialog.setNegativeButton("Cancel") { _, _ ->
            // Do nothing


        }
        val events = getEventsForDay(date)
        if (events.isEmpty()) {
            dialog.show()
        } else {
            val event = events[0]
//            val message = "Event Name: ${event.name}\\nEvent Date: ${event.time}"
//            dialog.setMessage(message)
//            dialog.setPositiveButton("OK") { _, _ ->
//                // Do nothing
//            }
//            dialog.show()
            Toast.makeText(this.requireContext(), " ${event.name}\n  time :  "+ event.time,Toast.LENGTH_LONG).show()

        }



    }

    private fun addEventToCalendar(date: CalendarDay, eventName: Editable, time:String) {
        val eventDecorator =
            EventDecorator(Color.RED, setOf(date), eventName.toString(), time)
        calendarView.addDecorator(eventDecorator)

        val event = Event(0,date.date.time,date.date.toString().substringBefore("00:00:00").trim(),eventName.toString(),time)

        mEventViewModel.addEvent(event)

    }


    private fun getEventsForDay(date: CalendarDay):List<Event> {
        return runBlocking {
            EventDatabase.getDatabase(requireContext()).eventDao().getEventsForDate(date.date.time)
        }
    }
//    @Override
//    override fun onResume() {
//
//
//        val mainActivity = activity as MainActivity
//        mainActivity.showanim(5)
////        mainActivity.setColor(ContextCompat.getColor(this.requireContext(), R.color.n8))
////        mainActivity.setIcon(ContextCompat.getColor(this.requireContext(), R.color.n4))
//        mainActivity.deactive(true)
//        super.onResume()
//    }


}
