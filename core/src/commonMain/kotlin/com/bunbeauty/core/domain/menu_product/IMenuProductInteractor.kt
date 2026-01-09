package com.bunbeauty.core.domain.menu_product

import com.bunbeauty.core.model.menu.MenuSection
import com.bunbeauty.core.model.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface IMenuProductInteractor {
    suspend fun getMenuSectionList(): List<MenuSection>

    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?>
}
