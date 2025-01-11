package com.bunbeauty.domain.feature.orderavailable

import com.bunbeauty.shared.domain.feature.orderavailable.IsOrderAvailableUseCase
import com.bunbeauty.shared.domain.model.order.WorkInfo
import com.bunbeauty.shared.domain.repo.WorkInfoRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetIsOrderAvailableUseCaseTest {

    private val workInfoRepository: WorkInfoRepo = mock()

    private var isOrderAvailableUseCase: IsOrderAvailableUseCase = IsOrderAvailableUseCase(
        workInfoRepository = workInfoRepository
    )

    @Test
    fun `invoke returns true when order is available`() = runTest {
        // Given
        val orderAvailable = workInfoMock.copy(workInfoType = WorkInfo.WorkInfoType.DELIVERY)
        everySuspend { workInfoRepository.getWorkInfo() } returns orderAvailable

        // When
        val result = isOrderAvailableUseCase.invoke()

        // Then
        assertTrue(result)
    }

    @Test
    fun `invoke returns false when order is not available`() = runTest {
        // Given
        val orderAvailable = workInfoMock.copy(workInfoType = WorkInfo.WorkInfoType.CLOSED)
        everySuspend { workInfoRepository.getWorkInfo() } returns orderAvailable

        // When
        val result = isOrderAvailableUseCase.invoke()

        // Then
        assertFalse(result)
    }

    @Test
    fun `invoke returns default true when order is null`() = runTest {
        // Given
        everySuspend { workInfoRepository.getWorkInfo() } returns null

        // When
        val result = isOrderAvailableUseCase.invoke()

        // Then
        assertTrue(result)
    }

    val workInfoMock = WorkInfo(workInfoType = WorkInfo.WorkInfoType.DELIVERY_AND_PICKUP)
}
