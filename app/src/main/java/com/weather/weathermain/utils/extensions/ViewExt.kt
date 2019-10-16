package com.weather.weathermain.utils.extensions

import android.content.Context
import android.widget.Toast
import com.orhanobut.logger.Logger

fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun prettyLog(msg: Any) {
    Logger.d(msg)
}