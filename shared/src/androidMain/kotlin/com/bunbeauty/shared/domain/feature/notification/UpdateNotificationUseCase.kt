package com.bunbeauty.shared.domain.feature.notification

import com.bunbeauty.shared.domain.repo.UserRepo
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

actual class UpdateNotificationUseCase(
    private val userRepository: UserRepo,
) {
    actual suspend operator fun invoke() {
        userRepository.updateNotificationTokenSuspend(notificationToken = getNotificationToken())
    }

    private suspend fun getNotificationToken(): String {
        return FirebaseMessaging.getInstance()
            .token
            .await()
    }
}