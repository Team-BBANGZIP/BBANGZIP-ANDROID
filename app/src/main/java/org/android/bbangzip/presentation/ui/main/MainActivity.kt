package org.android.bbangzip.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.android.bbangzip.presentation.ui.shared.SharedViewModel
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            val sharedViewModel: SharedViewModel = hiltViewModel()

            BBANGZIPANDROIDTheme {
                MainScreen(
                    navigator = navigator,
                    sharedViewModel = sharedViewModel,
                )
            }
        }
    }
}
