package com.bunbeauty.shared.domain.feature.auth

class CheckPhoneNumberUseCase {

    operator fun invoke(phoneNumber: String): Boolean {
        val phoneNumberRegex = Regex("^\\+7[0-9]{10}$")
        return phoneNumberRegex.matches(phoneNumber)
    }
}
