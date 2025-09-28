package org.android.bbangzip

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BbangZipApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setDarkMode()
        setTimber()
        setKakao()
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.Forest.plant(Timber.DebugTree())
    }

    private fun setKakao() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}