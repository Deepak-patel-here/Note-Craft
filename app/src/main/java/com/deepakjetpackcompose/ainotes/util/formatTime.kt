package com.deepakjetpackcompose.ainotes.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTime(time: Long): String {
    val date = Date(time)
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(date)
}