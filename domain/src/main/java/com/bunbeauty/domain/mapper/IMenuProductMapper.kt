package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.product.MenuProductEntity
import com.bunbeauty.domain.model.firebase.MenuProductFirebase
import com.bunbeauty.domain.model.ktor.MenuProductServer
import com.bunbeauty.domain.model.ui.product.MenuProduct

interface IMenuProductMapper {

    fun toFirebaseModel(menuProduct: MenuProduct): MenuProductFirebase
    fun toEntityModel(menuProduct: MenuProductFirebase): MenuProductEntity
    fun toEntityModel(menuProduct: MenuProductServer): MenuProductEntity
    fun toEntityModel(uuid: String, menuProduct: MenuProductFirebase): MenuProductEntity
    fun toUIModel(menuProduct: MenuProductEntity): MenuProduct
}