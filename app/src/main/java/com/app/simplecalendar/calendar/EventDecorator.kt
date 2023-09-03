package com.app.simplecalendar.calendar
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class EventDecorator(private val color: Int, dates: Collection<CalendarDay>, private val eventName: String, private val eventTime: String) :
    DayViewDecorator {

    private val dateSet: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dateSet.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(10F, color))
        view.addSpan(ForegroundColorSpan(color))
        view.addSpan(CenterTextSpan(eventName))
    }
    fun getEventName(): String {
        return eventName
    }

    fun getEventDate(): String {
        return eventTime
    }
}