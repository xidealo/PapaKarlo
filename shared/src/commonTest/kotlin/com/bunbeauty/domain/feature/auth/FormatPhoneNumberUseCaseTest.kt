package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.domain.feature.auth.FormatPhoneNumberUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FormatPhoneNumberUseCaseTest {

    private lateinit var formatPhoneNumberUseCase: FormatPhoneNumberUseCase

    @BeforeTest
    fun setup() {
        formatPhoneNumberUseCase = FormatPhoneNumberUseCase()
    }

    @Test
    fun `should return phone number code when delete it`() = runTest {
        val input = "+"
        val expected = "+7"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when input first digit`() = runTest {
        val input = "+71"
        val expected = "+7 (1"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when delete first digit`() = runTest {
        val input = "+7 ("
        val expected = "+7"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when delete parenthesis`() = runTest {
        val input = "+7 1"
        val expected = "+7 (1"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when input 4 digits`() = runTest {
        val input = "+7 (1234"
        val expected = "+7 (123) 4"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when delete 4th digit`() = runTest {
        val input = "+7 (123) "
        val expected = "+7 (123"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when delete space`() = runTest {
        val input = "+7 (123)4"
        val expected = "+7 (123) 4"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when input 7th digit`() = runTest {
        val input = "+7 (123) 4567"
        val expected = "+7 (123) 456-7"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when delete 7th digit`() = runTest {
        val input = "+7 (123) 456-"
        val expected = "+7 (123) 456"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when delete dash`() = runTest {
        val input = "+7 (123) 4567"
        val expected = "+7 (123) 456-7"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when input 9th digit`() = runTest {
        val input = "+7 (123) 456-789"
        val expected = "+7 (123) 456-78-9"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when delete 9th digit`() = runTest {
        val input = "+7 (123) 456-78-"
        val expected = "+7 (123) 456-78"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

    @Test
    fun `should return format phone number when input 11th digit`() = runTest {
        val input = "+7 (123) 456-78-901"
        val expected = "+7 (123) 456-78-90"

        val formatPhoneNumber = formatPhoneNumberUseCase(input)

        assertEquals(expected, formatPhoneNumber)
    }

}