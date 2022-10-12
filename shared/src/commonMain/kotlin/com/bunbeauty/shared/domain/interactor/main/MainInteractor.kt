package com.bunbeauty.shared.domain.interactor.main

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.withContext

class MainInteractor(
    private val orderRepo: OrderRepo,
    private val userInteractor: IUserInteractor,
    private val dataStoreRepo: DataStoreRepo
) : IMainInteractor {

    override suspend fun startCheckOrderUpdates() {
        coroutineScope {
            userInteractor.observeIsUserAuthorize().flatMapLatest { isUserAuthorize ->
                if (isUserAuthorize) {
                    val token = dataStoreRepo.getToken()
                    if (token != null) {
                        orderRepo.observeOrderUpdates(token)
                    } else {
                        orderRepo.stopCheckOrderUpdates()
                        flow { }
                    }
                } else {
                    orderRepo.stopCheckOrderUpdates()
                    flow { }
                }
            }.launchIn(this)
        }
    }

    override suspend fun stopCheckOrderUpdates() {
        orderRepo.stopCheckOrderUpdates()
    }
}