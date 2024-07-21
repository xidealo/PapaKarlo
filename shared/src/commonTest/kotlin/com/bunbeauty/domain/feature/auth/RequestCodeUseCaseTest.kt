package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.feature.auth.RequestCodeUseCase
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
    fun `should return Unit when code is requested successfully`() = runTest {
        val phoneNumber = "+71234567890"
        coEvery { authRepo.requestCode(phoneNumber) } returns true

        val result = requestCode(phoneNumber)

        assertEquals(Unit, result)
    }

    @Test
    fun `should throw SomethingWentWrongException when code request failed`() = runTest {
        val phoneNumber = "+71234567890"
        coEvery { authRepo.requestCode(phoneNumber) } returns false

        assertFailsWith(
            exceptionClass = SomethingWentWrongException::class,
            block = {
                requestCode(phoneNumber)
            }
        )
    }
}
