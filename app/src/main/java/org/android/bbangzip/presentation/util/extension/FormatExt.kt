package org.android.bbangzip.presentation.util.extension

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun Long.formatTime(): String {
    val seconds = this / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
