package com.bunbeauty.papakarlo.data.local.db.discount

import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.db.discount_product.DiscountProductRepo
import com.bunbeauty.papakarlo.data.model.discount.DiscountEntity
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class DiscountRepository @Inject constructor(
    private val discountDao: DiscountDao,
    private val apiRepository: IApiRepository,
    private val discountProductRepo: DiscountProductRepo
) : DiscountRepo {

    override suspend fun insert(discountEntity: DiscountEntity) {
        discountDao.insert(discountEntity)
    }

    /**
     * get discounts from server
     */
    override suspend fun getDiscounts() {
        apiRepository.getDiscounts().collect {
            it.map { discount ->
                insert(discount.discountEntity)
                discount.discountProducts.map { discountProduct ->
                    discountProduct.discountEntityUuid = discount.discountEntity.discountEntityUuid
                    discountProductRepo.insert(discountProduct)
                }
            }
        }
    }
}