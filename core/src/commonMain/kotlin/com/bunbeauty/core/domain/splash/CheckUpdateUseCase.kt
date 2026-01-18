package com.bunbeauty.core.domain.splash

import com.bunbeauty.core.domain.repo.VersionRepo

class CheckUpdateUseCase(
    private val versionRepo: VersionRepo,
    private val buildVersion: Long,
) {
    suspend operator fun invoke(): Boolean {
        val forceUpdateVersion = versionRepo.getForceUpdateVersion()
        return forceUpdateVersion <= buildVersion
    }
}
