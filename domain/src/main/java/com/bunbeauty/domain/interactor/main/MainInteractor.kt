package com.bunbeauty.domain.interactor.main

import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.OrderRepo
import com.bunbeauty.domain.worker.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainInteractor @Inject constructor(
    private val cityWorkerUtil: ICityWorkerUtil,
    private val categoryWorkerUtil: ICategoryWorkerUtil,
    private val menuProductWorkerUtil: IMenuProductWorkerUtil,
    private val deliveryWorkerUtil: IDeliveryWorkerUtil,
    private val userWorkerUtil: IUserWorkerUtil,
    @Api private val orderRepo: OrderRepo,
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

        val token = dataStoreRepo.getToken()
        if (token != null) {
            userWorkerUtil.refreshUser(token)
        }
    }

    override fun checkOrderUpdates() {
        userInteractor.observeIsUserAuthorize().flatMapLatest { isUserAuthorize ->
            if (isUserAuthorize) {
                val token = dataStoreRepo.getToken() ?: ""
                orderRepo.observeOrderUpdates(token).onEach { order ->
                    orderRepo.updateOrderStatus(order)
                }
            } else {
                orderRepo.stopCheckOrderUpdates()
                flow { }
            }
        }.launchIn(this)
    }
}