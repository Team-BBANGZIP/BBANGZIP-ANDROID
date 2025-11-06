package org.android.bbangzip.presentation.common.util.device

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private fun getDeviceType(context: Context): String {
        val smallestWidth = context.resources.configuration.smallestScreenWidthDp
        return when {
            smallestWidth >= 720 -> "DESKTOP"
            smallestWidth >= 600 -> "TABLET"
            else -> "PHONE"
        }
    }

    @Provides
    @Singleton
    fun provideDeviceInfo(
        @ApplicationContext context: Context,
    ): DeviceInfo {
        val appVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        val deviceType = getDeviceType(context)

        return DeviceInfoManager.getDeviceInfo(
            appVersion = appVersion!!,
            deviceType = deviceType,
        )
    }
}
