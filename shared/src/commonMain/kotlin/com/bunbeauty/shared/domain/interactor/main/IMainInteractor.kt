package com.bunbeauty.shared.domain.interactor.main

interface IMainInteractor {
    suspend fun startCheckOrderUpdates()
    suspend fun stopCheckOrderUpdates()
}