package com.bunbeauty.papakarlo.util.text_validator

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