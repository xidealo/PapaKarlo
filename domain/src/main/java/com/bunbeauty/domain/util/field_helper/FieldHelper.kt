package com.bunbeauty.domain.util.field_helper

import javax.inject.Inject

class FieldHelper @Inject constructor() : IFieldHelper {

    override fun isCorrectFieldContent(text: String, isRequired: Boolean, maxLength: Int): Boolean {
        return (text.isNotEmpty() || !isRequired) && (text.length <= maxLength)
    }

    override fun isCorrectFieldContent(
        text: String,
        isRequired: Boolean,
        minLength: Int,
        maxLength: Int
    ): Boolean {
        return isCorrectFieldContent(text, isRequired, maxLength) &&
                (text.length >= minLength)
    }

}