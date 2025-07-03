package com.bunbeauty.shared.domain.feature.notification

import cocoapods.FirebaseMessaging.FIRMessaging
import com.bunbeauty.core.Logger
import com.bunbeauty.shared.domain.repo.UserRepo
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.coroutines.suspendCoroutine

actual class UpdateNotificationUseCase(
    private val userRepository: UserRepo
) {
    actual suspend operator fun invoke() {
        try {
            userRepository.updateNotificationTokenSuspend(notificationToken = getNotificationToken())
        }
        catch (exception: Exception){
            Logger.logE("UpdateNotificationUseCase", exception.printStackTrace())
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private suspend fun getNotificationToken(): String {
        return suspendCoroutine { continuation ->
            FIRMessaging.messaging().tokenWithCompletion { token, error ->
                if (error != null) {
                    continuation.resumeWith(Result.failure(Exception(error.toString())))
                } else {
                    token?.let {
                        continuation.resumeWith(Result.success(it))
                    } ?: run {
                        continuation.resumeWith(Result.failure(Exception("Token is null")))
                    }
                }
            }
        }
    }
}
