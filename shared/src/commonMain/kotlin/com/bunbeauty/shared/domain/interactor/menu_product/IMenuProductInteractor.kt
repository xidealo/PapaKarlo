package com.bunbeauty.shared.domain.interactor.menu_product

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.menu.MenuSection
import com.bunbeauty.shared.domain.model.product.MenuProduct
import kotlinx.coroutines.flow.Flow

interface IMenuProductInteractor {

    suspend fun getMenuSectionList(): List<MenuSection>?
    fun observeMenuProductByUuid(menuProductUuid: String): Flow<MenuProduct?>

    fun observeMenuProductByUuidForSwift(menuProductUuid: String): CommonFlow<MenuProduct?>
    suspend fun getMenuProductByUuid(menuProductUuid: String): MenuProduct?

}