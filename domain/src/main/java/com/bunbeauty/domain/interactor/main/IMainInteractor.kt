package com.bunbeauty.domain.interactor.main

import kotlinx.coroutines.flow.Flow

interface IMainInteractor {

    fun checkOrderUpdates(isStartedFlow: Flow<Boolean>)
}