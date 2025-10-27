package org.android.bbangzip.presentation.common.util.device

import android.os.Build
import org.android.bbangzip.BuildConfig

object DeviceInfoManager {
    fun getDeviceInfo(): DeviceInfo {
        val osVersion = Build.VERSION.RELEASE
        val deviceName = Build.MODEL
        val osType = "Android"
        val appVersion = BuildConfig.VERSION_NAME

        return DeviceInfo(
            deviceName = deviceName,
            deviceType = deviceName,
            osVersion = osVersion,
            osType = osType,
            appVersion = appVersion,
        )
    }
}

data class DeviceInfo(
    val deviceName: String,
    val deviceType: String,
    val osVersion: String,
    val osType: String,
    val appVersion: String,
)