package com.bunbeauty.shared.domain.feature.notification

import cocoapods.FirebaseMessaging.FIRMessaging
import com.bunbeauty.shared.Constants
import kotlinx.cinterop.ExperimentalForeignApi

actual class SubscribeToNotificationUseCase {

    @OptIn(ExperimentalForeignApi::class)
    actual operator fun invoke(companyUuid: String) {
        FIRMessaging
        .messaging()
        .subscribeToTopic("${Constants.NEWS_NOTIFICATION_PREFIX}$companyUuid")
    }
}
