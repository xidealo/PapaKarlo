package com.bunbeauty.papakarlo.data.local.db.discount_product

import com.bunbeauty.papakarlo.data.model.discount.DiscountProduct
import javax.inject.Inject

class DiscountProductRepository @Inject constructor(
    private val discountProductDao: DiscountProductDao
) : DiscountProductRepo {
    override suspend fun insert(discountProduct: DiscountProduct) {
        discountProductDao.insert(discountProduct)
    }
}