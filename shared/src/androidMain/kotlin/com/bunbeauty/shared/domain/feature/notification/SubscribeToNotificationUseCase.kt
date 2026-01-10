package com.bunbeauty.shared.domain.feature.notification

import com.bunbeauty.core.Constants.NEWS_NOTIFICATION_PREFIX
import com.bunbeauty.core.Logger
import com.bunbeauty.core.Logger.NOTIFICATION_TAG
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

actual class SubscribeToNotificationUseCase {
    actual operator fun invoke(companyUuid: String) {
        Firebase.messaging
            .subscribeToTopic("$NEWS_NOTIFICATION_PREFIX$companyUuid")
            .addOnSuccessListener {
                Logger.logD(NOTIFICATION_TAG, "SubscribeToNotification success")
            }
    }
}
