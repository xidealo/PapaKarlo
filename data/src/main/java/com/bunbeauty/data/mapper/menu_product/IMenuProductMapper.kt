package com.bunbeauty.data.mapper.menu_product

import com.bunbeauty.domain.model.firebase.MenuProductFirebase
import com.bunbeauty.domain.model.ui.MenuProduct

interface IMenuProductMapper {

    fun toFirebaseModel(menuProduct: MenuProduct): MenuProductFirebase
}