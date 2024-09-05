package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.product.MenuProduct

interface MenuProductRepo {
    suspend fun getMenuProductList(): List<MenuProduct>
    suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct?
}
