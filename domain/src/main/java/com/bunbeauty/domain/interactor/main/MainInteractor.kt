package com.bunbeauty.domain.interactor.main

import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.worker.ICityWorkerUtil
import com.bunbeauty.domain.worker.IDeliveryWorkerUtil
import com.bunbeauty.domain.worker.IMenuProductWorkerUtil
import com.bunbeauty.domain.worker.IUserWorkerUtil
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val cityWorkerUtil: ICityWorkerUtil,
    private val menuProductWorkerUtil: IMenuProductWorkerUtil,
    private val deliveryWorkerUtil: IDeliveryWorkerUtil,
    private val userWorkerUtil: IUserWorkerUtil,
    private val dataStoreRepo: DataStoreRepo
) : IMainInteractor {

    override suspend fun refreshData() {
        cityWorkerUtil.refreshCityList()
        menuProductWorkerUtil.refreshMenuProductList()
        deliveryWorkerUtil.refreshDelivery()

        val token = dataStoreRepo.getToken()
        if (token != null) {
            userWorkerUtil.refreshUser(token)
        }
    }
}