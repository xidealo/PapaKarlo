package com.bunbeauty.domain.interactor.main

import kotlinx.coroutines.flow.Flow

interface IMainInteractor {

    suspend fun refreshData()
    fun checkOrderUpdates(isStartedFlow: Flow<Boolean>)
}