package com.weather.weathermain.utils.extensions

import android.content.Context
import android.widget.Toast
import com.orhanobut.logger.Logger

fun Context.showToastLong(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun prettyLog(msg: Any) {
    Logger.d(msg)
}