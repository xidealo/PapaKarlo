package com.bunbeauty.domain.util.field_helper

interface IFieldHelper {
    fun isCorrectFieldContent(text: String, isRequired: Boolean, maxLength: Int): Boolean

    fun isCorrectFieldContent(
        text: String,
        isRequired: Boolean,
        minLength: Int,
        maxLength: Int
    ): Boolean

}