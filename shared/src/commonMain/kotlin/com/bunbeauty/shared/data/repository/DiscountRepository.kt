package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.DiscountRepo
import com.bunbeauty.core.model.Discount
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.DiscountServer

class DiscountRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo,
) : CacheRepository<Discount>(),
    DiscountRepo {
    override val tag: String = "DISCOUNT_TAG"

    override suspend fun getDiscount(): Discount? =
        getCacheOrData(
            onApiRequest = networkConnector::getDiscount,
            onLocalRequest = dataStoreRepo::getDiscount,
            onSaveLocally = { discountServer ->
                dataStoreRepo.saveDiscount(toDiscount(discountServer))
            },
            serverToDomainModel = ::toDiscount,
        )

    private fun toDiscount(discountServer: DiscountServer): Discount = Discount(firstOrderDiscount = discountServer.discount)
}
