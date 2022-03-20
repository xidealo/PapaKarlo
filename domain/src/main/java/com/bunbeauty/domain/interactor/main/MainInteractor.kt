package com.bunbeauty.domain.interactor.main

import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.worker.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlin.coroutines.CoroutineContext

class MainInteractor(
    private val cityWorkerUtil: ICityWorkerUtil,
    private val categoryWorkerUtil: ICategoryWorkerUtil,
    private val menuProductWorkerUtil: IMenuProductWorkerUtil,
    private val deliveryWorkerUtil: IDeliveryWorkerUtil,
    private val userWorkerUtil: IUserWorkerUtil,
    private val orderRepo: OrderRepo,
    private val userInteractor: IUserInteractor,
    private val dataStoreRepo: DataStoreRepo
) : IMainInteractor, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job()

    override suspend fun refreshData() {
        cityWorkerUtil.refreshCityList()
        deliveryWorkerUtil.refreshDelivery()
        categoryWorkerUtil.refreshCategoryList()
        menuProductWorkerUtil.refreshMenuProductList()

        if (userInteractor.isUserAuthorize()) {
            val token = dataStoreRepo.getToken() ?: ""
            userWorkerUtil.refreshUser(token)
        }
    }

    override fun checkOrderUpdates(isStartedFlow: Flow<Boolean>) {
        isStartedFlow.flatMapLatest { isStarted ->
            userInteractor.observeIsUserAuthorize().flatMapLatest { isUserAuthorize ->
                if (isStarted && isUserAuthorize) {
                    val token = dataStoreRepo.getToken() ?: ""
                    userWorkerUtil.refreshUser(token)
                    orderRepo.observeOrderUpdates(token)
                } else {
                    orderRepo.stopCheckOrderUpdates()
                    flow { }
                }
            }
        }.launchIn(this)
    }
}