//package com.bunbeauty.domain.feature.auth
//
//import com.bunbeauty.shared.DataStoreRepo
//import com.bunbeauty.shared.domain.exeptions.SomethingWentWrongException
//import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
//import com.bunbeauty.shared.domain.model.AuthResponse
//import com.bunbeauty.shared.domain.repo.AuthRepo
//import kotlinx.coroutines.test.runTest
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertFailsWith
//
//class CheckCodeUseCaseTest {
//
//    private val authRepo: AuthRepo = mockk()
//    private val dataStoreRepo: DataStoreRepo = mockk()
//    lateinit var checkCodeUseCase: CheckCodeUseCase
//
//    @BeforeTest
//    fun setup() {
//        checkCodeUseCase = CheckCodeUseCase(
//            authRepo = authRepo,
//            dataStoreRepo = dataStoreRepo
//        )
//    }
//
//    @Test
//    fun `should save auth data when checking code is successful`() = runTest {
//        val code = "123456"
//        val token = "token"
//        val userUuid = "userUuid"
//        coEvery { authRepo.checkCode(code) } returns AuthResponse(
//            token = token,
//            userUuid = userUuid
//        )
//        coEvery { dataStoreRepo.saveToken(token) } returns Unit
//        coEvery { dataStoreRepo.saveUserUuid(userUuid) } returns Unit
//
//        checkCodeUseCase(code)
//
//        coVerify(exactly = 1) { dataStoreRepo.saveToken(token) }
//        coVerify(exactly = 1) { dataStoreRepo.saveUserUuid(userUuid) }
//    }
//
//    @Test
//    fun `should throw SomethingWentWrongException when checking code is failed`() = runTest {
//        val code = "123456"
//        coEvery { authRepo.checkCode(code) } returns null
//
//        assertFailsWith(
//            exceptionClass = SomethingWentWrongException::class,
//            block = {
//                checkCodeUseCase(code)
//            }
//        )
//    }
//}
