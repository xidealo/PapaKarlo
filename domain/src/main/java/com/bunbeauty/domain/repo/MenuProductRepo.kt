package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.ui.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface MenuProductRepo {

    suspend fun refreshMenuProducts()
    fun observeMenuProductList(): Flow<List<MenuProduct>>
    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?>
    suspend fun getMenuProduct(menuProductUuid: String): MenuProduct?
}