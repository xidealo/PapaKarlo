package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.DiscountServer
import com.bunbeauty.shared.domain.model.Discount

class DiscountRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo
) : CacheRepository<Discount>() {

    override val tag: String = "DISCOUNT_TAG"

    suspend fun getDiscount(): Discount? {
        return getCacheOrData(
            onApiRequest = networkConnector::getDiscount,
            onLocalRequest = dataStoreRepo::getDiscount,
            onSaveLocally = { discountServer ->
                dataStoreRepo.saveDiscount(toDiscount(discountServer))
            },
            serverToDomainModel = ::toDiscount
        )
    }

    private fun toDiscount(discountServer: DiscountServer): Discount {
        return Discount(firstOrderDiscount = discountServer.discount)
    }
}
