package com.bunbeauty.domain.interactor.update

import com.bunbeauty.domain.repo.VersionRepo
import javax.inject.Inject

class UpdateInteractor @Inject constructor(private val versionRepo: VersionRepo) :
    IUpdateInteractor {

    override suspend fun checkIsUpdated(currentVersion: Int): Boolean {
        val forceUpdateVersion = versionRepo.getForceUpdateVersion()
        return forceUpdateVersion <= currentVersion
    }

}