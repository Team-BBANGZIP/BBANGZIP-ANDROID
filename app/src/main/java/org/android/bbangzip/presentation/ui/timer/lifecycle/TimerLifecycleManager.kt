package org.android.bbangzip.presentation.ui.timer.lifecycle

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import org.android.bbangzip.presentation.common.observer.AppLifecycleObserver
import org.android.bbangzip.presentation.common.observer.ScreenStateReceiver
import timber.log.Timber

class TimerLifecycleManager
    @AssistedInject
    constructor(
        @ApplicationContext private val context: Context,
        @Assisted("onScreenOn") private val onScreenOnViewmodel: () -> Unit,
        @Assisted("onScreenOffByTimeout") private val onScreenOffByTimeout: () -> Unit,
        @Assisted("onScreenOffByLock") private val onScreenOffByLock: () -> Unit,
        @Assisted("onAppForeground") private val onAppForeground: (backgroundDuration: Long) -> Unit,
        @Assisted("onAppBackground") private val onAppBackgroundViewmodel: () -> Unit,
    ) {
        private val screenStateReceiver = ScreenStateReceiver()
        private val lifecycleObserver = AppLifecycleObserver()
        private var userInteractionReceiver: BroadcastReceiver? = null
        private var lastUserInteractionTime = System.currentTimeMillis()
        private val screenTimeoutMs = getScreenTimeout()

        init {
            setupScreenStateReceiver()
            setupLifecycleObserver()
            startUserInteractionTracking()
        }

        private fun setupScreenStateReceiver() {
            screenStateReceiver.setListener(
                object : ScreenStateReceiver.ScreenStateListener {
                    override fun onScreenOn() {
                        lastUserInteractionTime = System.currentTimeMillis()
                        onScreenOnViewmodel()
                    }

                    override fun onScreenOff() {
                        val timeSinceLastInteraction = System.currentTimeMillis() - lastUserInteractionTime
                        val lockPressThreshold = screenTimeoutMs -1000L


                        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                        val isLocked = keyguardManager.isKeyguardLocked

                        Timber.d("Screen OFF | timeSinceLastInteraction=$timeSinceLastInteraction | isLocked=$isLocked | screenTimeoutMs=$screenTimeoutMs")


                        if (isLocked || timeSinceLastInteraction < lockPressThreshold) {
                            onScreenOffByLock()
                        } else {
                            onScreenOffByTimeout()
                        }
                    }
                },
            )

            val filter =
                IntentFilter().apply {
                    addAction(Intent.ACTION_SCREEN_OFF)
                    addAction(Intent.ACTION_SCREEN_ON)
                    addAction(Intent.ACTION_USER_PRESENT)
                }
            context.registerReceiver(screenStateReceiver, filter)
        }

        private fun setupLifecycleObserver() {
            lifecycleObserver.setListener(
                object : AppLifecycleObserver.AppLifecycleListener {
                    override fun onAppForeground() {
                        onAppForeground(lifecycleObserver.getBackgroundDuration())
                    }

                    override fun onAppBackground() {
                        onAppBackgroundViewmodel()
                    }
                },
            )
            ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
        }

        private fun startUserInteractionTracking() {
            val userInteractionFilter =
                IntentFilter().apply {
                    addAction(Intent.ACTION_USER_PRESENT)
                }

            userInteractionReceiver =
                object : BroadcastReceiver() {
                    override fun onReceive(
                        context: Context?,
                        intent: Intent?,
                    ) {
                        when (intent?.action) {
                            Intent.ACTION_USER_PRESENT -> {
                                lastUserInteractionTime = System.currentTimeMillis()
                            }
                        }
                    }
                }

            context.registerReceiver(userInteractionReceiver, userInteractionFilter)
        }

        private fun getScreenTimeout(): Long {
            return try {
                val timeout =
                    Settings.System.getLong(
                        context.contentResolver,
                        Settings.System.SCREEN_OFF_TIMEOUT,
                    )
                return timeout
            } catch (e: Settings.SettingNotFoundException) {
                15000L
            }
        }

        fun cleanup() {
            try {
                context.unregisterReceiver(screenStateReceiver)
            } catch (e: IllegalArgumentException) {
                Timber.d("ScreenStateReceiver not registered: ${e.message}")
            }

            try {
                userInteractionReceiver?.let {
                    context.unregisterReceiver(it)
                    userInteractionReceiver = null
                }
            } catch (e: IllegalArgumentException) {
                Timber.d("UserInteractionReceiver not registered: ${e.message}")
            }

            ProcessLifecycleOwner.get().lifecycle.removeObserver(lifecycleObserver)
        }

        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted("onScreenOn") onScreenOn: () -> Unit,
                @Assisted("onScreenOffByTimeout") onScreenOffByTimeout: () -> Unit,
                @Assisted("onScreenOffByLock") onScreenOffByLock: () -> Unit,
                @Assisted("onAppForeground") onAppForeground: (backgroundDuration: Long) -> Unit,
                @Assisted("onAppBackground") onAppBackground: () -> Unit,
            ): TimerLifecycleManager
        }
    }
