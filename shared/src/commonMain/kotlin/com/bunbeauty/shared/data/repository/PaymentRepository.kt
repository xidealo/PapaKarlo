package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.payment_method.IPaymentMethodDao
import com.bunbeauty.shared.data.mapper.payment.PaymentMethodMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.PaymentMethodServer
import com.bunbeauty.shared.data.repository.base.CacheListRepository
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo

class PaymentRepository(
    private val networkConnector: NetworkConnector,
    private val paymentMethodMapper: PaymentMethodMapper,
    private val paymentMethodDao: IPaymentMethodDao
) : CacheListRepository<PaymentMethod>(), PaymentRepo {

    override val tag: String = "PAYMENT_TAG"

    override suspend fun getPaymentMethodList(): List<PaymentMethod> {
        return getCacheOrListData(
            onApiRequest = networkConnector::getPaymentMethodList,
            onLocalRequest = ::getLocalPaymentMethodList,
            onSaveLocally = ::savePaymentMethodListLocally,
            serverToDomainModel = paymentMethodMapper::toPaymentMethod
        )
    }

    private suspend fun getLocalPaymentMethodList(): List<PaymentMethod> {
        return paymentMethodDao.getPaymentMethodList().map(paymentMethodMapper::toPaymentMethod)
    }

    private suspend fun savePaymentMethodListLocally(paymentMethodServerList: List<PaymentMethodServer>) {
        paymentMethodDao.insertPaymentMethodList(
            paymentMethodServerList.map(paymentMethodMapper::toPaymentMethodEntity)
        )
    }
}
