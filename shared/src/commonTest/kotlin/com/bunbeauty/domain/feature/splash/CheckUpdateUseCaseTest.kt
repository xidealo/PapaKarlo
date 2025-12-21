package com.bunbeauty.domain.feature.splash

import com.bunbeauty.shared.domain.feature.splash.CheckUpdateUseCase
import com.bunbeauty.shared.domain.repo.VersionRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckUpdateUseCaseTest {
    private val versionRepo: VersionRepo = mock()

    @Test
    fun `returns true when currentVersion is equal to or above forceUpdateVersion`() =
        runTest {
            // Given
            val currentVersion = 5L
            val forceUpdateVersion = 5
            val checkUpdateUseCase: CheckUpdateUseCase =
                CheckUpdateUseCase(
                    versionRepo = versionRepo,
                    buildVersion = currentVersion,
                )
            everySuspend { versionRepo.getForceUpdateVersion() } returns forceUpdateVersion

            // When
            val result = checkUpdateUseCase.invoke()

            // Then
            assertTrue(result)
        }

    @Test
    fun `returns false when currentVersion is below forceUpdateVersion`() =
        runTest {
            // Given
            val currentVersion = 3L
            val forceUpdateVersion = 5
            val checkUpdateUseCase: CheckUpdateUseCase =
                CheckUpdateUseCase(
                    versionRepo = versionRepo,
                    buildVersion = currentVersion,
                )
            everySuspend { versionRepo.getForceUpdateVersion() } returns forceUpdateVersion

            // When
            val result = checkUpdateUseCase.invoke()

            // Then
            assertFalse(result)
        }
}
