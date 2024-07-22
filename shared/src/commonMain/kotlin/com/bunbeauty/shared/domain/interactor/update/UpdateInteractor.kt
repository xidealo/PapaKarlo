package com.bunbeauty.shared.domain.interactor.update

import com.bunbeauty.shared.domain.repo.VersionRepo

class UpdateInteractor(
    private val versionRepo: VersionRepo
) : IUpdateInteractor {

    override suspend fun checkIsUpdated(currentVersion: Int): Boolean {
        val forceUpdateVersion = versionRepo.getForceUpdateVersion()
        return forceUpdateVersion <= currentVersion
    }
}
