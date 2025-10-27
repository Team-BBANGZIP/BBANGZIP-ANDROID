package org.android.bbangzip.presentation.common.util.device

import android.os.Build

object DeviceInfoManager {
    fun getDeviceInfo(appVersion: String, deviceType: String): DeviceInfo {
        val osVersion = Build.VERSION.RELEASE
        val deviceName = Build.MODEL
        val osType = "Android"

        return DeviceInfo(
            deviceName = deviceName,
            deviceType = deviceType,
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