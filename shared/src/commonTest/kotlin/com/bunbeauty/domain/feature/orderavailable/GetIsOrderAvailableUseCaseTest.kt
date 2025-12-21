package com.bunbeauty.domain.feature.orderavailable

import com.bunbeauty.shared.domain.feature.orderavailable.IsOrderAvailableUseCase
import com.bunbeauty.shared.domain.model.company.Company
import com.bunbeauty.shared.domain.repo.CompanyRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetIsOrderAvailableUseCaseTest {
    private val companyRepo: CompanyRepo = mock()

    private var isOrderAvailableUseCase: IsOrderAvailableUseCase =
        IsOrderAvailableUseCase(
            companyRepo = companyRepo,
        )

    @Test
    fun `invoke should return true when orders are available`() =
        runTest {
            // Arrange
            val company = Company(isOrderAvailable = true)
            everySuspend { companyRepo.getCompany() } returns company

            // Act
            val result = isOrderAvailableUseCase()

            // Assert
            assertEquals(true, result)
        }

    @Test
    fun `invoke should return false when orders are not available`() =
        runTest {
            // Arrange
            val company = Company(isOrderAvailable = false)
            everySuspend { companyRepo.getCompany() } returns
                company.copy(
                    isOrderAvailable = false,
                )

            // Act
            val result = isOrderAvailableUseCase()

            // Assert
            assertEquals(false, result)
        }

    val companyMock = Company(isOrderAvailable = true)
}
