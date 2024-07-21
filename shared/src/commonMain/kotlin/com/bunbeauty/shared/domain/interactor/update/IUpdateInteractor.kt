package com.bunbeauty.shared.domain.interactor.update

interface IUpdateInteractor {

    suspend fun checkIsUpdated(currentVersion: Int): Boolean
}
