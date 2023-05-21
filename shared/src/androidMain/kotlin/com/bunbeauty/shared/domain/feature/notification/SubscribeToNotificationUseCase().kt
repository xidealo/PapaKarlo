package com.bunbeauty.shared.domain.feature.notification

import com.bunbeauty.shared.Constants.NEWS_NOTIFICATION_PREFIX
import com.bunbeauty.shared.Logger
import com.bunbeauty.shared.Logger.NOTIFICATION_TAG
import com.bunbeauty.shared.data.companyUuid
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

actual class SubscribeToNotificationUseCase {

    actual operator fun invoke() {
        Firebase.messaging.subscribeToTopic("$NEWS_NOTIFICATION_PREFIX$companyUuid")
            .addOnSuccessListener {
                Logger.logD(NOTIFICATION_TAG, "SubscribeToNotification success")
            }
    }
}