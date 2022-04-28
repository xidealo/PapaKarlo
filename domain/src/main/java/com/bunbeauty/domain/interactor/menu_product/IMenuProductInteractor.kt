package com.bunbeauty.domain.interactor.menu_product

import com.bunbeauty.domain.model.menu.MenuSection
import com.bunbeauty.domain.model.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface IMenuProductInteractor {

    suspend fun getMenuSectionList(): List<MenuSection>?
    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?>

}