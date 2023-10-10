package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.domain.exeptions.InvalidPhoneNumberException
import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.exeptions.TooManyRequestsException
import com.bunbeauty.shared.domain.feature.auth.RequestCodeUseCase
import com.bunbeauty.shared.domain.model.CodeResponse
import com.bunbeauty.shared.domain.repo.AuthRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RequestCodeUseCaseTest {

    private val authRepo: AuthRepo = mockk()
    private lateinit var requestCode: RequestCodeUseCase

    @BeforeTest
    fun setup() {
        requestCode = RequestCodeUseCase(
            authRepo = authRepo
        )
    }

    @Test
    fun `should throw InvalidPhoneNumberException when phone number is not complete`() = runTest {
        val phoneNumber = "+7 (123) 456-78-9"

        assertFailsWith<InvalidPhoneNumberException> {
            requestCode(phoneNumber)
        }
    }

    @Test
    fun `should return Unit when code is requested successfully`() = runTest {
        val phoneNumber = "+7 (123) 456-78-90"
        val formattedPhoneNumber = "+71234567890"
        coEvery { authRepo.requestCode(formattedPhoneNumber) } returns CodeResponse.SUCCESS

        val result = requestCode(phoneNumber)

        assertEquals(Unit, result)
    }

    @Test
    fun `should throw TooManyRequestsException when code request failed`() = runTest {
        val phoneNumber = "+7 (123) 456-78-90"
        val formattedPhoneNumber = "+71234567890"
        coEvery { authRepo.requestCode(formattedPhoneNumber) } returns CodeResponse.TOO_MANY_REQUESTS_ERROR

        assertFailsWith<TooManyRequestsException> {
            requestCode(phoneNumber)
        }
    }

    @Test
    fun `should throw SomethingWentWrongException when code request failed`() = runTest {
        val phoneNumber = "+7 (123) 456-78-90"
        val formattedPhoneNumber = "+71234567890"
        coEvery { authRepo.requestCode(formattedPhoneNumber) } returns CodeResponse.SOMETHING_WENT_WRONG_ERROR

        assertFailsWith<SomethingWentWrongException> {
            requestCode(phoneNumber)
        }
    }

}