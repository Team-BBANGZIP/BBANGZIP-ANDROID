package org.android.bbangzip.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.android.bbangzip.R
import org.android.bbangzip.presentation.common.util.extension.Gap
import org.android.bbangzip.ui.theme.BBANGZIPANDROIDTheme
import org.android.bbangzip.ui.theme.BbangZipTheme

@Composable
fun SplashScreen() {
    val splashGradient =
        Brush.linearGradient(
            colorStops =
                arrayOf(
                    0.0415f to BbangZipTheme.color.backgroundNormal_FFFFFF,
                    0.9243f to BbangZipTheme.color.backgroundAlternative_FAF6F3,
                ),
        )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(splashGradient)
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(top = 263.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.width(width = 252.dp),
            painter = painterResource(R.drawable.img_service_logo),
            contentDescription = null,
        )

        Gap()

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_bbangzip_team_info),
            contentDescription = null,
            tint = BbangZipTheme.color.primaryNormal_897869,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    BBANGZIPANDROIDTheme {
        SplashScreen()
    }
}
