package com.bunbeauty.domain.util.code

import com.bunbeauty.common.Constants.CODE_DIVIDER
import com.bunbeauty.common.Constants.CODE_NUMBER_COUNT
import javax.inject.Inject

class CodeGenerator @Inject constructor() : ICodeGenerator {

    override fun generateCode(currentMillis: Long, letters: String): String {
        val currentSeconds = currentMillis / 1000

        val number = (currentSeconds % (letters.length * CODE_NUMBER_COUNT)).toInt()
        val codeLetter = letters[number % letters.length].toString()
        val codeNumber = (number / letters.length)
        val codeNumberString = if (codeNumber < 10) {
            "0$codeNumber"
        } else {
            codeNumber.toString()
        }

        return codeLetter + CODE_DIVIDER + codeNumberString
    }
}