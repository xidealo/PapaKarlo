package com.bunbeauty.domain.feature.auth

import com.bunbeauty.core.domain.auth.CheckPhoneNumberUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckPhoneNumberUseCaseTest {
    lateinit var checkPhoneNumberUseCase: CheckPhoneNumberUseCase

    @BeforeTest
    fun setup() {
        checkPhoneNumberUseCase = CheckPhoneNumberUseCase()
    }

    @Test
    fun `should return false when phone number is not complete`() =
        runTest {
            val phoneNumber = "+7123456789"

            val isCorrect = checkPhoneNumberUseCase(phoneNumber)

            assertFalse(isCorrect)
        }

    @Test
    fun `should return true when phone number is completed`() =
        runTest {
            val phoneNumber = "+71234567890"

            val isCorrect = checkPhoneNumberUseCase(phoneNumber)

            assertTrue(isCorrect)
        }
}
