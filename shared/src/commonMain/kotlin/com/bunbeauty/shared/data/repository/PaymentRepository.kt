package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.payment_method.IPaymentMethodDao
import com.bunbeauty.shared.data.mapper.payment.PaymentMethodMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.core.model.payment_method.PaymentMethod
import com.bunbeauty.core.domain.repo.PaymentRepo
import com.bunbeauty.core.extension.getNullableResult

class PaymentRepository(
    private val networkConnector: NetworkConnector,
    private val paymentMethodMapper: PaymentMethodMapper,
    private val paymentMethodDao: IPaymentMethodDao,
) : PaymentRepo {
    private var paymentMethodListCache: List<PaymentMethod>? = null

    override suspend fun getPaymentMethodList(): List<PaymentMethod> {
        val cache = paymentMethodListCache
        return if (cache == null) {
            val paymentMethodList = getRemotePaymentMethodList()
            if (paymentMethodList == null) {
                getLocalPaymentMethodList()
            } else {
                savePaymentMethodListLocally(paymentMethodList)
                paymentMethodListCache = paymentMethodList
                paymentMethodList
            }
        } else {
            cache
        }
    }

    suspend fun getRemotePaymentMethodList(): List<PaymentMethod>? =
        networkConnector
            .getPaymentMethodList()
            .getNullableResult { paymentMethodServerList ->
                paymentMethodServerList.results.mapNotNull(paymentMethodMapper::toPaymentMethod)
            }

    suspend fun getLocalPaymentMethodList(): List<PaymentMethod> =
        paymentMethodDao
            .getPaymentMethodList()
            .mapNotNull(paymentMethodMapper::toPaymentMethod)

    suspend fun savePaymentMethodListLocally(paymentMethodList: List<PaymentMethod>) {
        paymentMethodDao.insertPaymentMethodList(
            paymentMethodList.map(paymentMethodMapper::toPaymentMethodEntity),
        )
    }
}
