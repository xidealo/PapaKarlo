package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.payment_method.IPaymentMethodDao
import com.bunbeauty.shared.data.mapper.payment.PaymentMethodMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.api_result_handler.ApiResultHandler
import com.bunbeauty.shared.data.network.model.PaymentServer
import com.bunbeauty.shared.domain.model.Payment
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.domain.repo.PaymentRepo

class PaymentRepository(
    private val networkConnector: NetworkConnector,
    private val paymentMethodMapper: PaymentMethodMapper,
    private val paymentMethodDao: IPaymentMethodDao,
    apiResultHandler: ApiResultHandler
) : PaymentRepo,
    ApiResultHandler by apiResultHandler {

    private var paymentMethodListCache: List<PaymentMethod>? = null

    override suspend fun getPaymentMethodList(token: String): List<PaymentMethod> {
        val cache = paymentMethodListCache
        return if (cache == null) {
            val paymentMethodList = getRemotePaymentMethodList(token)
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

    suspend fun getRemotePaymentMethodList(token: String): List<PaymentMethod>? {
        return networkConnector.getPaymentMethodList(token)
            .getNullableResult { paymentMethodServerList ->
                paymentMethodServerList.mapNotNull(paymentMethodMapper::toPaymentMethod)
            }
    }

    suspend fun getLocalPaymentMethodList(): List<PaymentMethod> {
        return paymentMethodDao.getPaymentMethodList()
            .mapNotNull(paymentMethodMapper::toPaymentMethod)
    }

    suspend fun savePaymentMethodListLocally(paymentMethodList: List<PaymentMethod>) {
        paymentMethodDao.insertPaymentMethodList(
            paymentMethodList.map(paymentMethodMapper::toPaymentMethodEntity)
        )
    }

    suspend fun getPayment(token: String): Payment? {
        return null
    }
}