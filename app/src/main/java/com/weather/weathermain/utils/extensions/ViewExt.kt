package com.weather.weathermain.utils.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}