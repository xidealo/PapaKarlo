package com.bunbeauty.papakarlo.util.text_validator

import com.bunbeauty.shared.Constants

class TextValidator : ITextValidator {
    override fun isPhoneNumberCorrect(phoneNumber: String): Boolean {
        return Regex("\\${Constants.PHONE_CODE} \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}")
            .matches(phoneNumber)
    }
}
