package com.bunbeauty.shared.domain.feature.splash

import com.bunbeauty.shared.domain.repo.VersionRepo

class CheckUpdateUseCase(
    private val versionRepo: VersionRepo,
) {
    suspend operator fun invoke(currentVersion: Int): Boolean {
        val forceUpdateVersion = versionRepo.getForceUpdateVersion()
        return forceUpdateVersion <= currentVersion
    }
}
