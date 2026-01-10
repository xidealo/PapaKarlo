package com.bunbeauty.domain.feature.auth

import com.bunbeauty.core.domain.auth.CheckCodeUseCase
import com.bunbeauty.core.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.core.domain.repo.AuthRepo
import com.bunbeauty.core.model.AuthResponse
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class CheckCodeUseCaseTest {
    private val authRepo: AuthRepo = mock()
    private val checkCodeUseCase: CheckCodeUseCase =
        CheckCodeUseCase(
            authRepo = authRepo,
        )

    @Test
    fun `should save auth data when checking code is successful`() =
        runTest {
            val code = "123456"
            val token = "token"
            val userUuid = "userUuid"
            everySuspend { authRepo.checkCode(code) } returns
                AuthResponse(
                    token = token,
                    userUuid = userUuid,
                )
            everySuspend { authRepo.saveToken(token) } returns Unit
            everySuspend { authRepo.saveUserUuid(userUuid) } returns Unit

            checkCodeUseCase(code)

            verifySuspend { authRepo.saveToken(token) }
            verifySuspend { authRepo.saveUserUuid(userUuid) }
        }

    @Test
    fun `should throw SomethingWentWrongException when checking code is failed`() =
        runTest {
            val code = "123456"
            everySuspend { authRepo.checkCode(code) } returns null

            assertFailsWith(
                exceptionClass = SomethingWentWrongException::class,
                block = {
                    checkCodeUseCase(code)
                },
            )
        }
}
