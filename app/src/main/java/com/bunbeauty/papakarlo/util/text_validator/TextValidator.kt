package com.bunbeauty.papakarlo.util.text_validator

import com.bunbeauty.common.Constants


class TextValidator : ITextValidator {

    override fun isPhoneNumberCorrect(phoneNumber: String): Boolean {
        return Regex("\\${Constants.PHONE_CODE} \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}")
            .matches(phoneNumber)
    }

    override fun isFieldContentCorrect(text: String, minLength: Int?, maxLength: Int): Boolean {
        return (minLength == null || text.length >= minLength) && (text.length <= maxLength)
    }

    override fun isRequiredFieldContentCorrect(
        text: String,
        minLength: Int?,
        maxLength: Int
    ): Boolean {
        return text.isNotEmpty() && isFieldContentCorrect(text, minLength, maxLength)
    }

}