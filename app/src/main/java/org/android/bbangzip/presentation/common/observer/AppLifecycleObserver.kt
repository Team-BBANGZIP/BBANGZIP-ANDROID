package org.android.bbangzip.presentation.common.observer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class AppLifecycleObserver : DefaultLifecycleObserver {
    interface AppLifecycleListener {
        fun onAppForeground()

        fun onAppBackground()
    }

    private var listener: AppLifecycleListener? = null
    private var backgroundStartTime = 0L
    private var isScreenOn = true

    fun setListener(listener: AppLifecycleListener) {
        this.listener = listener
    }

    fun updateScreenState(isScreenOn: Boolean) {
        this.isScreenOn = isScreenOn
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        listener?.onAppForeground()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        if (isScreenOn) {
            backgroundStartTime = System.currentTimeMillis()
            listener?.onAppBackground()
        }
    }

    fun getBackgroundDuration(): Long {
        return if (backgroundStartTime > 0) {
            System.currentTimeMillis() - backgroundStartTime
        } else {
            0L
        }
    }
}
