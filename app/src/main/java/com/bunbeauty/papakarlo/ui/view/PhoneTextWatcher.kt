package com.bunbeauty.papakarlo.ui.view

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

class PhoneTextWatcher(private val phoneEditText: TextInputEditText) : TextWatcher {

    @SuppressLint("SetTextI18n")
    override fun afterTextChanged(text: Editable?) {
        var cursorPosition = phoneEditText.selectionEnd

        val numbers = text.toString()
            .replace("+7", "")
            .replace("7 (", "")
            .replace(Regex("\\D"), "")
        val firstGroup = numbers.take(3)
        val secondGroup = numbers.drop(3).take(3)
        val thirdGroup = numbers.drop(6).take(2)
        val fourthGroup = numbers.drop(8).take(2)

        var result = ""
        if (firstGroup.isNotEmpty()) {
            result += "+7 ($firstGroup"
        }

        if (secondGroup.isNotEmpty()) {
            result += ") $secondGroup"
        }

        if (thirdGroup.isNotEmpty()) {
            result += "-$thirdGroup"
        }

        if (fourthGroup.isNotEmpty()) {
            result += "-$fourthGroup"
        }

        when (cursorPosition) {
            1 -> {
                // "0" -> "+7 (0"
                if (text?.first() != '+') {
                    cursorPosition += 4
                }
            }
            4 -> {
                // "+7 (" -> ""
                if (text.toString() == "+7 (") {
                    cursorPosition -= 4
                }
            }
            8 -> {
                // "+7 (0000" -> "+7 (000) 0"
                if (text!![7] != ')') {
                    cursorPosition += 2
                }
            }
            9 -> {
                // "+7 (000) " -> "+7 (000"
                if (text!![8] == ' ') {
                    cursorPosition -= 2
                }

                // "+7 (000)0" -> "+7 (000) 0"
                else {
                    cursorPosition++
                }
            }
            13 -> {
                // "+7 (000) 000-" -> "+7 (000) 000"
                if (text?.last() == '-') {
                    cursorPosition--
                }

                // "+7 (000) 0000" -> "+7 (000) 000-0"
                else {
                    if (text!![12] != '-') {
                        cursorPosition++
                    }
                }
            }
            16 -> {
                // "+7 (000) 000-00-" -> "+7 (000) 000-00"
                if (text?.last() == '-') {
                    cursorPosition--
                }

                // "+7 (000) 000-000" -> "+7 (000) 000-00-0"
                else {
                    if (text!![15] != '-') {
                        cursorPosition++
                    }
                }
            }
            19 -> {
                // "+7 (000) 000-00-000" -> "+7 (000) 000-00-00"
                cursorPosition--
            }
        }

        if (text.toString() != result) {
            phoneEditText.setText(result)
            if (cursorPosition > result.length) {
                cursorPosition = result.length
            }
            phoneEditText.setSelection(cursorPosition)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}