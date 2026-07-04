package com.bunbeauty.auth.domain

import com.bunbeauty.core.domain.repo.UserRepo

actual class UpdateNotificationUseCase(
    private val userRepository: UserRepo,
) {
    actual suspend operator fun invoke() {
        // Push notification token is not available on the web target.
    }
}
