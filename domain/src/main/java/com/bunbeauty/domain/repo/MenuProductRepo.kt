package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface MenuProductRepo {

    suspend fun refreshMenuProductList()
    fun observeMenuProductList(): Flow<List<MenuProduct>>
    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?>
    suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct?
}