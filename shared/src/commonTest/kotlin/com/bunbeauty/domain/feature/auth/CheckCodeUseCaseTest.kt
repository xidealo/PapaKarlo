package com.bunbeauty.domain.feature.auth

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.exeptions.AuthSessionTimeoutException
import com.bunbeauty.shared.domain.exeptions.InvalidCodeException
import com.bunbeauty.shared.domain.exeptions.NoAttemptsException
import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.model.AuthResponseNew
import com.bunbeauty.shared.domain.repo.AuthRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class CheckCodeUseCaseTest {

    private val authRepo: AuthRepo = mockk()
    private val dataStoreRepo: DataStoreRepo = mockk()
    lateinit var checkCodeUseCase: CheckCodeUseCase

    @BeforeTest
    fun setup() {
        checkCodeUseCase = CheckCodeUseCase(
            authRepo = authRepo,
            dataStoreRepo = dataStoreRepo,
        )
    }

    @Test
    fun `should save auth data when checking code is successful`() = runTest {
        val code = "123456"
        val token = "token"
        val userUuid = "userUuid"
        coEvery{ authRepo.checkCode(code) } returns AuthResponseNew.Success(
            token = token,
            userUuid = userUuid,
        )
        coEvery { dataStoreRepo.saveToken(token) } returns Unit
        coEvery { dataStoreRepo.saveUserUuid(userUuid) } returns Unit

        checkCodeUseCase(code)

        coVerify(exactly = 1) { dataStoreRepo.saveToken(token) }
        coVerify(exactly = 1) { dataStoreRepo.saveUserUuid(userUuid) }
    }

    @Test
    fun `should throw InvalidCodeException when checking code error`() = runTest {
        val code = "123456"
        coEvery{ authRepo.checkCode(code) } returns AuthResponseNew.Error.INVALID_CODE_ERROR

        assertFailsWith<InvalidCodeException> {
            checkCodeUseCase(code)
        }
    }

    @Test
    fun `should throw NoAttemptsException when checking code error`() = runTest {
        val code = "123456"
        coEvery{ authRepo.checkCode(code) } returns AuthResponseNew.Error.NO_ATTEMPTS_ERROR

        assertFailsWith<NoAttemptsException> {
            checkCodeUseCase(code)
        }
    }

    @Test
    fun `should throw AuthSessionTimeoutException when checking code error`() = runTest {
        val code = "123456"
        coEvery{ authRepo.checkCode(code) } returns AuthResponseNew.Error.AUTH_SESSION_TIMEOUT_ERROR

        assertFailsWith<AuthSessionTimeoutException> {
            checkCodeUseCase(code)
        }
    }

    @Test
    fun `should throw SomethingWentWrongException when checking code error`() = runTest {
        val code = "123456"
        coEvery{ authRepo.checkCode(code) } returns AuthResponseNew.Error.SOMETHING_WENT_WRONG_ERROR

        assertFailsWith<SomethingWentWrongException> {
            checkCodeUseCase(code)
        }
    }

}