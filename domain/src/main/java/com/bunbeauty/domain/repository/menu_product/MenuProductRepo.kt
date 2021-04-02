package com.bunbeauty.domain.repository.menu_product

import com.bunbeauty.data.model.MenuProduct
import kotlinx.coroutines.flow.Flow

interface MenuProductRepo {

    suspend fun insert(menuProduct: MenuProduct)

    suspend fun getMenuProductRequest()
    fun getMenuProductList(): Flow<List<MenuProduct>>
}