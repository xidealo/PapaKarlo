package com.bunbeauty.shared.domain.feature.auth

import com.bunbeauty.shared.Constants

class FormatPhoneNumberUseCase {

    operator fun invoke(phoneNumber: String): String {
        val numbers = phoneNumber.run {
            if (!contains(Constants.PHONE_CODE) && isNotEmpty()) {
                ""
            } else {
                this
            }
        }.replace(Constants.PHONE_CODE, "")
            .replace(Regex("\\D"), "")
        val firstGroup = numbers.take(3)
        val secondGroup = numbers.drop(3).take(3)
        val thirdGroup = numbers.drop(6).take(2)
        val fourthGroup = numbers.drop(8).take(2)
        var result = Constants.PHONE_CODE
        if (firstGroup.isNotEmpty()) {
            result += " ($firstGroup"
        }
        if (secondGroup.isNotEmpty()) {
            result += ") $secondGroup"
        }
        if (thirdGroup.isNotEmpty()) {
            result += "-$thirdGroup"
        }
        if (fourthGroup.isNotEmpty()) {
            result += "-$fourthGroup"
        }

        return result
    }

}