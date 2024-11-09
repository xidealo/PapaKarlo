package com.bunbeauty.domain.feature.splash

import com.bunbeauty.shared.domain.feature.splash.CheckUpdateUseCase
import com.bunbeauty.shared.domain.repo.VersionRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class CheckUpdateUseCaseTest {

    private val versionRepo: VersionRepo = mock()
    private val checkUpdateUseCase: CheckUpdateUseCase =
        CheckUpdateUseCase(versionRepo = versionRepo)

    @Test
    fun `returns true when currentVersion is equal to or above forceUpdateVersion`() = runTest {
        // Given
        val currentVersion = 5
        val forceUpdateVersion = 5
        everySuspend { versionRepo.getForceUpdateVersion() } returns forceUpdateVersion

        // When
        val result = checkUpdateUseCase.invoke(currentVersion)

        // Then
        assertTrue(result)
    }

    @Test
    fun `returns false when currentVersion is below forceUpdateVersion`() = runTest {
        // Given
        val currentVersion = 3
        val forceUpdateVersion = 5
        everySuspend { versionRepo.getForceUpdateVersion() } returns forceUpdateVersion

        // When
        val result = checkUpdateUseCase.invoke(currentVersion)

        // Then
        assertFalse(result)
    }
}
