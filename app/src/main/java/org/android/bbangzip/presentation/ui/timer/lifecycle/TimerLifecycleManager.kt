package org.android.bbangzip.presentation.ui.timer.lifecycle

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ProcessLifecycleOwner
import org.android.bbangzip.presentation.observer.AppLifecycleObserver
import org.android.bbangzip.presentation.observer.ScreenStateReceiver
import org.android.bbangzip.presentation.ui.timer.TimerContract
import timber.log.Timber

class TimerLifecycleManager(
    private val context: Context,
    private val onEvent: (TimerContract.TimerEvent) -> Unit
) {
    private val screenStateReceiver = ScreenStateReceiver()
    private val lifecycleObserver = AppLifecycleObserver()

    init {
        setupScreenStateReceiver()
        setupLifecycleObserver()
    }

    private fun setupScreenStateReceiver() {
        screenStateReceiver.setListener(object : ScreenStateReceiver.ScreenStateListener {
            override fun onScreenOn() {
                onEvent(TimerContract.TimerEvent.OnScreenUnlocked)
            }

            override fun onScreenOff() {
                onEvent(TimerContract.TimerEvent.OnScreenLocked)
            }
        })

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
        }
        context.registerReceiver(screenStateReceiver, filter)
    }


    private fun setupLifecycleObserver() {
        lifecycleObserver.setListener(object : AppLifecycleObserver.AppLifecycleListener {
            override fun onAppForeground() {
                val backgroundDuration = lifecycleObserver.getBackgroundDuration()
                onEvent(TimerContract.TimerEvent.OnAppForeground(backgroundDuration))
            }

            override fun onAppBackground() {
                onEvent(TimerContract.TimerEvent.OnAppBackground)
            }
        })

        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    fun cleanup() {
        try {
            context.unregisterReceiver(screenStateReceiver)
        } catch (e: IllegalArgumentException) {
            Timber.d("ScreenStateReceiver not registered: ${e.message}")
        }
        ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
    }
}
