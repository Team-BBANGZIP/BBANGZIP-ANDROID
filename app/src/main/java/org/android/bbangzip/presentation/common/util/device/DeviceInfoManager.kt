package org.android.bbangzip.presentation.common.util.device

import android.os.Build

object DeviceInfoManager {
    fun getDeviceInfo(deviceType: String): DeviceInfo {
        val osVersion = Build.VERSION.RELEASE
        val deviceName = Build.MODEL
        val osType = "Android"

        return DeviceInfo(
            deviceName = deviceName,
            deviceType = deviceType,
            osVersion = osVersion,
            osType = osType,
        )
    }

    fun getAppInfo(
        appVersion: String,
        packageName: String,
    ): AppInfo {
        return AppInfo(
            appVersion = appVersion,
            packageName = packageName,
        )
    }
}

data class DeviceInfo(
    val deviceName: String,
    val deviceType: String,
    val osVersion: String,
    val osType: String,
)

data class AppInfo(
    val appVersion: String,
    val packageName: String,
)
