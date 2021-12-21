package com.bunbeauty.domain.interactor.update

interface IUpdateInteractor {

    suspend fun checkIsUpdated(currentVersion: Int): Boolean
}