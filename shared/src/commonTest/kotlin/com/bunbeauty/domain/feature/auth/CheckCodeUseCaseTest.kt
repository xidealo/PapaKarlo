package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.model.AuthResponse
import com.bunbeauty.shared.domain.repo.AuthRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class CheckCodeUseCaseTest {

    private val authRepo: AuthRepo = mock()
    private val dataStoreRepo: DataStoreRepo = mock()
    private val checkCodeUseCase: CheckCodeUseCase = CheckCodeUseCase(
        authRepo = authRepo,
        dataStoreRepo = dataStoreRepo
    )

    @Test
    fun `should save auth data when checking code is successful`() = runTest {
        val code = "123456"
        val token = "token"
        val userUuid = "userUuid"
        everySuspend { authRepo.checkCode(code) } returns AuthResponse(
            token = token,
            userUuid = userUuid
        )
        everySuspend { dataStoreRepo.saveToken(token) } returns Unit
        everySuspend { dataStoreRepo.saveUserUuid(userUuid) } returns Unit

        checkCodeUseCase(code)

        verifySuspend { dataStoreRepo.saveToken(token) }
        verifySuspend { dataStoreRepo.saveUserUuid(userUuid) }
    }

    @Test
    fun `should throw SomethingWentWrongException when checking code is failed`() = runTest {
        val code = "123456"
        everySuspend { authRepo.checkCode(code) } returns null

        assertFailsWith(
            exceptionClass = SomethingWentWrongException::class,
            block = {
                checkCodeUseCase(code)
            }
        )
    }
}
