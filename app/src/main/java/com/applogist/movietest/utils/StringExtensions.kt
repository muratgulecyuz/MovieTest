package com.applogist.movietest.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.formatDate(format: String = ONLY_YEAR_FORMAT): String {
    val date = SimpleDateFormat(SERVICE_DATE_FORMAT).parse(this)
    return SimpleDateFormat(format).format(date)
}