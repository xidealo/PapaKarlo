package com.bunbeauty.domain.interactor.menu_product

import com.bunbeauty.domain.model.MenuModel
import kotlinx.coroutines.flow.Flow

interface IMenuProductInteractor {

    fun observeMenuList(): Flow<List<MenuModel>>
}