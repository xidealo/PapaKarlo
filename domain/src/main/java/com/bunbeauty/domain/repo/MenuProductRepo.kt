package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface MenuProductRepo {

    suspend fun refreshMenuProductList()
    suspend fun getMenuProductList(): List<MenuProduct>
    fun observeMenuProductList(): Flow<List<MenuProduct>>
    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?>
}