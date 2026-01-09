package com.bunbeauty.shared.data.mapper.cart_product

import com.bunbeauty.core.model.addition.CartProductAddition
import com.bunbeauty.core.model.cart.CartProduct
import com.bunbeauty.core.model.category.Category
import com.bunbeauty.core.model.product.MenuProduct
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity

class CartProductMapper : ICartProductMapper {
    override fun toCartProduct(
        cartProductWithMenuProductEntityList: List<CartProductWithMenuProductEntity>,
        menuProductWithCategoryEntityList: List<MenuProductWithCategoryEntity>,
    ): CartProduct {
        val firstCartProductWithCategoryEntity = cartProductWithMenuProductEntityList.first()
        return CartProduct(
            uuid = firstCartProductWithCategoryEntity.cartProductUuid,
            count = firstCartProductWithCategoryEntity.count,
            product =
                MenuProduct(
                    uuid = firstCartProductWithCategoryEntity.menuProductUuid,
                    name = firstCartProductWithCategoryEntity.name,
                    newPrice = firstCartProductWithCategoryEntity.newPrice,
                    oldPrice = firstCartProductWithCategoryEntity.oldPrice,
                    utils = firstCartProductWithCategoryEntity.utils,
                    nutrition = firstCartProductWithCategoryEntity.nutrition,
                    description = firstCartProductWithCategoryEntity.description,
                    comboDescription = firstCartProductWithCategoryEntity.comboDescription,
                    photoLink = firstCartProductWithCategoryEntity.photoLink,
                    categoryList =
                        menuProductWithCategoryEntityList.map { menuProductWithCategoryEntity ->
                            Category(
                                uuid = menuProductWithCategoryEntity.categoryUuid,
                                name = menuProductWithCategoryEntity.categoryName,
                                priority = menuProductWithCategoryEntity.categoryPriority,
                            )
                        },
                    visible = firstCartProductWithCategoryEntity.visible,
                    isRecommended = firstCartProductWithCategoryEntity.isRecommended,
                    additionGroups = emptyList(),
                ),
            additionList =
                cartProductWithMenuProductEntityList.mapNotNull { cartProductWithMenuProductEntity ->
                    cartProductWithMenuProductEntity.cartProductAdditionUuid?.let {
                        CartProductAddition(
                            uuid = cartProductWithMenuProductEntity.cartProductAdditionUuid,
                            name = cartProductWithMenuProductEntity.cartProductAdditionName ?: "",
                            price = cartProductWithMenuProductEntity.cartProductAdditionPrice,
                            cartProductUuid = cartProductWithMenuProductEntity.cartProductUuid,
                            additionUuid = cartProductWithMenuProductEntity.cartProductAdditionAdditionUuid
                                ?: "",
                            fullName = cartProductWithMenuProductEntity.cartProductAdditionFullName,
                            priority = cartProductWithMenuProductEntity.cartProductAdditionPriority,
                        )
                    }
                },
        )
    }

    override fun toEntityModel(cartProduct: CartProduct): CartProductEntity =
        CartProductEntity(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid,
        )
}
