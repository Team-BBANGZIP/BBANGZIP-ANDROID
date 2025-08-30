package org.android.bbangzip.presentation.ui.timer.lifecycle

import android.content.BroadcastReceiver
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
    private var lastUserInteractionTime = System.currentTimeMillis()

    init {
        setupScreenStateReceiver()
        setupLifecycleObserver()
        startUserInteractionTracking()
    }

    private fun setupScreenStateReceiver() {
        screenStateReceiver.setListener(object : ScreenStateReceiver.ScreenStateListener {
            override fun onScreenOn() {
                lifecycleObserver.updateScreenState(true)
                onEvent(TimerContract.TimerEvent.OnScreenTurnedOn)
            }

            override fun onScreenOff() {
                val timeSinceLastInteraction = System.currentTimeMillis() - lastUserInteractionTime
                lifecycleObserver.updateScreenState(isScreenOn = false)

                if (timeSinceLastInteraction <= 2000) {
                    onEvent(TimerContract.TimerEvent.OnLockButtonPressed)
                } else {
                    onEvent(TimerContract.TimerEvent.OnScreenTimeOut)
                }
            }
        })

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_USER_PRESENT)
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

    private fun startUserInteractionTracking() {
        val userInteractionFilter = IntentFilter().apply {
            addAction(Intent.ACTION_USER_PRESENT)
            addAction(Intent.ACTION_SCREEN_ON)
        }

        val userInteractionReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_USER_PRESENT, Intent.ACTION_SCREEN_ON -> {
                        lastUserInteractionTime = System.currentTimeMillis()
                    }
                }
            }
        }

        context.registerReceiver(userInteractionReceiver, userInteractionFilter)
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
