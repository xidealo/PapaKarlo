package com.bunbeauty.domain.interactor.menu_product

import com.bunbeauty.domain.model.MenuModel
import com.bunbeauty.domain.model.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface IMenuProductInteractor {

    fun observeMenuList(): Flow<List<MenuModel>>
    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?>
    fun getCurrentMenuPosition(currentCategoryUuid: String, menuList: List<MenuModel>): Int
}