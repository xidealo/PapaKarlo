package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.ui.MenuProduct
import kotlinx.coroutines.flow.Flow

interface MenuProductRepo {

    suspend fun insert(menuProduct: MenuProduct)

    suspend fun getMenuProductRequest()
    fun getMenuProductListAsFlow(): Flow<List<MenuProduct>>
    fun getMenuProductAsFlow(menuProductUuid: String): Flow<MenuProduct?>
    suspend fun getMenuProduct(menuProductUuid: String): MenuProduct?
}