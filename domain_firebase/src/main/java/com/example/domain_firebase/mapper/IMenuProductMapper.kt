package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.product.MenuProduct
import com.bunbeauty.domain.model.product.OrderMenuProduct
import com.example.domain_firebase.model.entity.product.MenuProductEntity
import com.example.domain_firebase.model.firebase.MenuProductFirebase

interface IMenuProductMapper {

    fun toFirebaseModel(menuProduct: MenuProduct): MenuProductFirebase
    fun toFirebaseModel(orderMenuProduct: OrderMenuProduct): MenuProductFirebase
    fun toEntityModel(uuid: String, menuProduct: MenuProductFirebase): MenuProductEntity
    fun toUIModel(menuProduct: MenuProductEntity): MenuProduct
    fun toOrderMenuProduct(menuProduct: MenuProductEntity): OrderMenuProduct
}