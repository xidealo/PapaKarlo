package com.bunbeauty.papakarlo.data.local.db.cart_product

import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.db.order.OrderDao
import com.bunbeauty.papakarlo.data.model.CartProduct
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartProductRepository @Inject constructor(
    private val iApiRepository: IApiRepository,
    private val cartProductDao: CartProductDao
) : CartProductRepo {

    override suspend fun insertCartProduct(cartProduct: CartProduct): CartProduct {
        cartProduct.id = cartProductDao.insert(cartProduct)
        return cartProduct
    }

    override suspend fun insertCartProductAsync(cartProduct: CartProduct) =
        withContext(Dispatchers.IO) {
            async {
                cartProduct.uuid = iApiRepository.insertCartProduct(cartProduct)
                insertCartProduct(cartProduct)
            }
        }
}