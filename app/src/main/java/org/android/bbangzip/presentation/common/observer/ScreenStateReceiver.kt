package org.android.bbangzip.presentation.common.observer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenStateReceiver : BroadcastReceiver() {
    interface ScreenStateListener {
        fun onScreenOn()

        fun onScreenOff()
    }

    private var listener: ScreenStateListener? = null

    fun setListener(listener: ScreenStateListener) {
        this.listener = listener
    }

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        when (intent.action) {
            Intent.ACTION_SCREEN_OFF -> listener?.onScreenOff()
            Intent.ACTION_SCREEN_ON -> listener?.onScreenOn()
        }
    }
}
