package com.bunbeauty.shared.domain.feature.notification

import cocoapods.FirebaseMessaging.FIRMessaging
import com.bunbeauty.shared.Constants
import com.bunbeauty.shared.data.companyUuid

actual class SubscribeToNotificationUseCase {

    actual operator fun invoke() {
        FIRMessaging
            .messaging()
            .subscribeToTopic("${Constants.NEWS_NOTIFICATION_PREFIX}$companyUuid")
    }
}