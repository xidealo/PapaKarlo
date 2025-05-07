package com.bunbeauty.shared.domain.feature.notification

import com.bunbeauty.shared.data.repository.UserRepository

actual class UpdateNotificationUseCase(
    private val userRepository: UserRepository,
) {
    actual suspend operator fun invoke() {
        //userRepository()
    }
}