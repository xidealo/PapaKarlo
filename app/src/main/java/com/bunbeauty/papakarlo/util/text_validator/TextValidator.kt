package com.bunbeauty.papakarlo.util.text_validator


class TextValidator  constructor() : ITextValidator {

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