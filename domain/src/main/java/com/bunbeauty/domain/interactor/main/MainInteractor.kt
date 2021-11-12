package com.bunbeauty.domain.interactor.main

import com.bunbeauty.domain.auth.IAuthUtil
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
    private val authUtil: IAuthUtil,
) : IMainInteractor {

    override fun refreshData() {
        cityWorkerUtil.refreshCityList()
        menuProductWorkerUtil.refreshMenuProductList()
        deliveryWorkerUtil.refreshDelivery()

        val userUuid = authUtil.userUuid
        val userPhone = authUtil.userPhone
        if (authUtil.isAuthorize && userUuid != null && userPhone != null) {
            userWorkerUtil.refreshUser(userUuid, userPhone)
        }
    }
}