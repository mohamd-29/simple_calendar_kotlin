package com.app.simplecalendar.calendar

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan

class CenterTextSpan(private val text: String) : LineBackgroundSpan {

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val width = paint.measureText(text.subSequence(start, end).toString())
        val x = (left + right) / 2f - width / 2f
        canvas.drawText(text, start, end, x, baseline.toFloat(), paint)
    }
}