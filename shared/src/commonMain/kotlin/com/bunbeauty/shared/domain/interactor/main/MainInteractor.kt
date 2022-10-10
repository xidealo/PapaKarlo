package com.bunbeauty.shared.domain.interactor.main

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.repo.OrderRepo
import kotlinx.coroutines.flow.collect

class MainInteractor(
    private val orderRepo: OrderRepo,
    private val userInteractor: IUserInteractor,
    private val dataStoreRepo: DataStoreRepo
) : IMainInteractor {

    override suspend fun startCheckOrderUpdates() {
        userInteractor.observeIsUserAuthorize().collect { isUserAuthorize ->
            if (isUserAuthorize) {
                dataStoreRepo.getToken()?.let { token ->
                    orderRepo.observeOrderUpdates(token).collect()
                }
            } else {
                orderRepo.stopCheckOrderUpdates()
            }
        }
    }

    override suspend fun stopCheckOrderUpdates() {
        orderRepo.stopCheckOrderUpdates()
    }
}