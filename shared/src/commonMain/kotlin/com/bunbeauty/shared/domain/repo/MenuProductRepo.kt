package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface MenuProductRepo {
    suspend fun getMenuProductList(): List<MenuProduct>
    suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct?
}
