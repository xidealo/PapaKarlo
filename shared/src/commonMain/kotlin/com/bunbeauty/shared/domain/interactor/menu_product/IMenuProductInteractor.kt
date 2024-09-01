package com.bunbeauty.shared.domain.interactor.menu_product

import com.bunbeauty.shared.domain.model.menu.MenuSection

interface IMenuProductInteractor {
    suspend fun getMenuSectionList(): List<MenuSection>
}
