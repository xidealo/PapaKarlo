package com.bunbeauty.domain.util.validator

interface ITextValidator {

    fun isFieldContentCorrect(
        text: String,
        minLength: Int? = null,
        maxLength: Int,
    ): Boolean

    fun isRequiredFieldContentCorrect(
        text: String,
        minLength: Int? = null,
        maxLength: Int,
    ): Boolean

}