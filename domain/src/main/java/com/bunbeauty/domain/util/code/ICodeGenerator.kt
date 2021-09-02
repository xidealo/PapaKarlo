package com.bunbeauty.domain.util.code

interface ICodeGenerator {

    fun generateCode(currentMillis: Long, letters: String): String
}