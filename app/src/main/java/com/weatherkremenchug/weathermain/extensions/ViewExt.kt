package com.weatherkremenchug.weathermain.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}