package com.bunbeauty.shared.domain.feature.notification

import cocoapods.FirebaseMessaging.FIRMessaging
import com.bunbeauty.shared.domain.repo.UserRepo
import kotlinx.cinterop.ExperimentalForeignApi

actual class UpdateNotificationUseCase(
    private val userRepository: UserRepo,
) {
    actual suspend operator fun invoke() {
        userRepository.updateNotificationTokenSuspend(notificationToken = getNotificationToken())
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun getNotificationToken(): String {
        return FIRMessaging
            .messaging()
            .FCMToken
            .orEmpty()
    }
}