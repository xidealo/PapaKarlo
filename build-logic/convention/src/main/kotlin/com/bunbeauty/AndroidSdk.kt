package com.bunbeauty

import com.android.build.api.dsl.CompileSdkSpec

internal object AndroidSdk {
    internal const val MIN = 26
    internal const val COMPILE = 37
    internal const val COMPILE_MINOR = 0
    internal const val TARGET = COMPILE
}

internal fun CompileSdkSpec.setFoodDeliveryCompileSdk() {
    version =
        release(AndroidSdk.COMPILE) {
            minorApiLevel = AndroidSdk.COMPILE_MINOR
        }
}
