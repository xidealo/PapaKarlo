package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCaseImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FormatPhoneNumberUseCaseTest {

    private lateinit var formatPhoneNumber: FormatPhoneNumberUseCaseImpl

    @BeforeTest
    fun setup() {
        formatPhoneNumber = FormatPhoneNumberUseCaseImpl()
    }

    @Test
    fun `should return phone number code when deleting it`() = runTest {
        val input = "+"
        val expected = "+7"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when inputting first digit`() = runTest {
        val input = "+71"
        val expected = "+7 (1"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when deleting first digit`() = runTest {
        val input = "+7 ("
        val expected = "+7"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when deleting parenthesis`() = runTest {
        val input = "+7 1"
        val expected = "+7 (1"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when inputting 4 digits`() = runTest {
        val input = "+7 (1234"
        val expected = "+7 (123) 4"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when deleting 4th digit`() = runTest {
        val input = "+7 (123) "
        val expected = "+7 (123"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when deleting space`() = runTest {
        val input = "+7 (123)4"
        val expected = "+7 (123) 4"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when inputting 7th digit`() = runTest {
        val input = "+7 (123) 4567"
        val expected = "+7 (123) 456-7"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when deleting 7th digit`() = runTest {
        val input = "+7 (123) 456-"
        val expected = "+7 (123) 456"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when deleting dash`() = runTest {
        val input = "+7 (123) 4567"
        val expected = "+7 (123) 456-7"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when inputting 9th digit`() = runTest {
        val input = "+7 (123) 456-789"
        val expected = "+7 (123) 456-78-9"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when deleting 9th digit`() = runTest {
        val input = "+7 (123) 456-78-"
        val expected = "+7 (123) 456-78"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when inputting 11th digit`() = runTest {
        val input = "+7 (123) 456-78-901"
        val expected = "+7 (123) 456-78-90"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when inputting 11th digit in the middle`() = runTest {
        val input = "+7 (123) 4556-78-90"
        val expected = "+7 (123) 455-67-89"

        val formatPhoneNumber = formatPhoneNumber(input)

        assertEquals(expected, formatPhoneNumber)
    }
}
