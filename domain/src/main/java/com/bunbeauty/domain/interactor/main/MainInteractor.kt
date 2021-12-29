package com.bunbeauty.domain.interactor.main

import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.worker.*
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val cityWorkerUtil: ICityWorkerUtil,
    private val categoryWorkerUtil: ICategoryWorkerUtil,
    private val menuProductWorkerUtil: IMenuProductWorkerUtil,
    private val deliveryWorkerUtil: IDeliveryWorkerUtil,
    private val userWorkerUtil: IUserWorkerUtil,
    private val dataStoreRepo: DataStoreRepo
) : IMainInteractor {

    override suspend fun refreshData() {
        cityWorkerUtil.refreshCityList()
        categoryWorkerUtil.refreshCategoryList()
        menuProductWorkerUtil.refreshMenuProductList()
        deliveryWorkerUtil.refreshDelivery()

        val token = dataStoreRepo.getToken()
        if (token != null) {
            userWorkerUtil.refreshUser(token)
        }
    }
}