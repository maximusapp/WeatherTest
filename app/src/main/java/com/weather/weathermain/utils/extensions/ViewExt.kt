package com.weather.weathermain.utils.extensions

import android.content.Context
import android.widget.Toast
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToastLong(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun prettyLog(msg: Any) {
    Logger.d(msg)
}

fun currentDayFormat(time: Long) : String? {
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(time)
}

fun currentMonthFormat(time: Long) : String? {
    return SimpleDateFormat("MMMM, d", Locale.getDefault()).format(time)
}

fun timeFormat(time: Long) : String? {
    return SimpleDateFormat("hh:mm", Locale.getDefault()).format(time * 1000L)
}
