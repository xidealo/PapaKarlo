package com.bunbeauty.shared.domain.feature.login

class GetPhoneNumberCursorPositionUseCase(
    private val formatPhoneNumber: FormatPhoneNumberUseCase
) {

    operator fun invoke(phoneNumber: String, cursorPosition: Int): Int {
        var newPosition = cursorPosition
        when (cursorPosition) {
            0, 1 -> {
                newPosition = 2
            }

            3 -> {
                // "+70" -> "+7 (0"
                if (phoneNumber[2].isDigit()) {
                    newPosition = 5
                }
            }

            8 -> {
                // "+7 (0000" -> "+7 (000) 0"
                if (phoneNumber[7].isDigit()) {
                    newPosition = 10
                }
            }

            13 -> {
                // "+7 (000) 0000" -> "+7 (000) 000-0"
                if (phoneNumber[12].isDigit()) {
                    newPosition = 14
                }
            }

            16 -> {
                // "+7 (000) 000-000" -> "+7 (000) 000-00-0"
                if (phoneNumber[15].isDigit()) {
                    newPosition = 17
                }
            }
        }

        val formatPhoneNumber = formatPhoneNumber(phoneNumber)
        return if (newPosition > formatPhoneNumber.length) {
            formatPhoneNumber.length
        } else {
            newPosition
        }
    }

}