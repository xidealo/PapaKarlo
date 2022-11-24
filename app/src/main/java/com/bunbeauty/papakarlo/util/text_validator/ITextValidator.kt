package com.bunbeauty.papakarlo.util.text_validator

interface ITextValidator {

    fun isPhoneNumberCorrect(phoneNumber: String): Boolean

    fun isFieldContentCorrect(text: String, minLength: Int? = null, maxLength: Int): Boolean
}
