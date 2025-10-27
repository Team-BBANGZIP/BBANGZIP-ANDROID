package org.android.bbangzip.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import org.android.bbangzip.data.auth.interceptor.AuthEvent
import org.android.bbangzip.data.auth.interceptor.AuthEventManager
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authEventManager: AuthEventManager

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            val sharedViewModel: SharedViewModel = hiltViewModel()
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(Unit) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    authEventManager.authEvent.collect { event ->
                        if (event is AuthEvent.ForceLogout) {
                            navigator.navigateToLoginAndClearStack()
                        }
                    }
                }
            }

            BBANGZIPANDROIDTheme {
                MainScreen(
                    navigator = navigator,
                    sharedViewModel = sharedViewModel,
                )
            }
        }
    }
}
