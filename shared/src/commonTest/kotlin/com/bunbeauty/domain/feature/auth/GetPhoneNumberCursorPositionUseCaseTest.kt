package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCase
import com.bunbeauty.shared.domain.feature.auth.GetPhoneNumberCursorPositionUseCase
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetPhoneNumberCursorPositionUseCaseTest {
    private val formatPhoneNumber: FormatPhoneNumberUseCase = mock()
    private val getPhoneNumberCursorPosition: GetPhoneNumberCursorPositionUseCase =
        GetPhoneNumberCursorPositionUseCase(
            formatPhoneNumber = formatPhoneNumber,
        )

    @Test
    fun `should return cursor position when editing phone number code`() =
        runTest {
            val input = "+ (123"
            val inputCursorPosition = 1
            val formatPhoneNumber = "+7"
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, inputCursorPosition)

            assertEquals(formatPhoneNumber.length, cursorPosition)
        }

    @Test
    fun `should return cursor position when inputting first digit`() =
        runTest {
            val input = "+71"
            val formatPhoneNumber = "+7 (1"
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, input.length)

            assertEquals(formatPhoneNumber.length, cursorPosition)
        }

    @Test
    fun `should return cursor position when deleting first digit`() =
        runTest {
            val input = "+7 ("
            val formatPhoneNumber = "+7"
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, input.length)

            assertEquals(formatPhoneNumber.length, cursorPosition)
        }

    @Test
    fun `should return cursor position when inputting 4th digit`() =
        runTest {
            val input = "+7 (1234"
            val formatPhoneNumber = "+7 (123) 4"
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, input.length)

            assertEquals(formatPhoneNumber.length, cursorPosition)
        }

    @Test
    fun `should return cursor position when deleting 4th digit`() =
        runTest {
            val input = "+7 (123) "
            val formatPhoneNumber = "+7 (123"
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, input.length)

            assertEquals(formatPhoneNumber.length, cursorPosition)
        }

    @Test
    fun `should return cursor position when deleting non-digit`() =
        runTest {
            val input = "+7 123"
            val inputCursorPosition = 3
            val formatPhoneNumber = "+7 (123"
            val expectedCursorPosition = 3
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, inputCursorPosition)

            assertEquals(expectedCursorPosition, cursorPosition)
        }

    @Test
    fun `should return cursor position when inputting 11th digit`() =
        runTest {
            val input = "+7 (123) 456-78-901"
            val formatPhoneNumber = "+7 (123) 456-78-90"
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, input.length)

            assertEquals(formatPhoneNumber.length, cursorPosition)
        }

    @Test
    fun `should return cursor position when inputting 11th digit in the middle`() =
        runTest {
            val input = "+7 (123) 4556-78-90"
            val inputCursorPosition = 12
            val formatPhoneNumber = "+7 (123) 455-67-89"
            val expectedCursorPosition = 12
            every { formatPhoneNumber(input) } returns formatPhoneNumber

            val cursorPosition = getPhoneNumberCursorPosition(input, inputCursorPosition)

            assertEquals(expectedCursorPosition, cursorPosition)
        }
}
