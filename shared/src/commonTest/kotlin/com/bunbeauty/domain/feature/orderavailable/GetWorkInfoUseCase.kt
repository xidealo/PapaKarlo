package com.bunbeauty.domain.feature.orderavailable

import com.bunbeauty.shared.domain.feature.orderavailable.GetWorkInfoUseCase
import com.bunbeauty.shared.domain.model.order.WorkInfo
import com.bunbeauty.shared.domain.repo.WorkInfoRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetWorkInfoUseCaseTest {

    private val workInfoRepository: WorkInfoRepo = mock()
    private val getWorkInfoUseCase: GetWorkInfoUseCase = GetWorkInfoUseCase(
        workInfoRepository = workInfoRepository
    )

    @Test
    fun `returns work info when repository provides data`() = runTest {
        // Given
        val expectedWorkInfo =
            workInfoMock.copy(workInfoType = WorkInfo.WorkInfoType.DELIVERY_AND_PICKUP)
        everySuspend { workInfoRepository.getWorkInfo() } returns expectedWorkInfo

        // When
        val result = getWorkInfoUseCase.invoke()

        // Then
        assertEquals(expectedWorkInfo, result)
    }

    @Test
    fun `returns null when repository provides no data`() = runTest {
        // Given
        everySuspend { workInfoRepository.getWorkInfo() } returns null

        // When
        val result = getWorkInfoUseCase.invoke()

        // Then
        assertNull(result)
    }

    private val workInfoMock = WorkInfo(workInfoType = WorkInfo.WorkInfoType.DELIVERY_AND_PICKUP)
}
