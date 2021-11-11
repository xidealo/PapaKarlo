package com.bunbeauty.domain.interactor.user

interface IUserInteractor {

    suspend fun refreshUser()
    fun logout()
}