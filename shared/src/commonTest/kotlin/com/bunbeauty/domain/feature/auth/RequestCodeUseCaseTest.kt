package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.domain.feature.auth.RequestCodeUseCase
import com.bunbeauty.shared.domain.repo.AuthRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

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
        coEvery { authRepo.requestCode(phoneNumber) } returns Unit

        val result = requestCode(phoneNumber)

        assertEquals(Unit, result)
    }

}