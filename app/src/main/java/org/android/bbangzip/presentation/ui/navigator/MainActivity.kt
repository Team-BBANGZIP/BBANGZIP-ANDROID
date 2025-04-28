package org.android.bbangzip.presentation.ui.navigator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigator: MainNavigator = rememberMainNavigator()

            BBANGZIPANDROIDTheme {
                MainScreen(navigator = navigator)
            }
        }
    }
}
