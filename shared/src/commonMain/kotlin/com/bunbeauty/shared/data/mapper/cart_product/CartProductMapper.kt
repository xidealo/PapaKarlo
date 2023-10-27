package com.bunbeauty.shared.data.mapper.cart_product

import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct

class CartProductMapper : ICartProductMapper {

    override fun toCartProductList(cartProductWithMenuProductEntityList: List<CartProductWithMenuProductEntity>): List<CartProduct> {
        return cartProductWithMenuProductEntityList.groupBy { cartProductWithMenuProductEntity ->
            cartProductWithMenuProductEntity.cartProductUuid
        }.map { (_, groupedCartProductWithCategoryEntityList) ->
            val firstCartProductWithCategoryEntity =
                groupedCartProductWithCategoryEntityList.first()
            CartProduct(
                uuid = firstCartProductWithCategoryEntity.cartProductUuid,
                count = firstCartProductWithCategoryEntity.count,
                product = MenuProduct(
                    uuid = firstCartProductWithCategoryEntity.menuProductUuid,
                    name = firstCartProductWithCategoryEntity.name,
                    newPrice = firstCartProductWithCategoryEntity.newPrice,
                    oldPrice = firstCartProductWithCategoryEntity.oldPrice,
                    utils = firstCartProductWithCategoryEntity.utils,
                    nutrition = firstCartProductWithCategoryEntity.nutrition,
                    description = firstCartProductWithCategoryEntity.description,
                    comboDescription = firstCartProductWithCategoryEntity.comboDescription,
                    photoLink = firstCartProductWithCategoryEntity.photoLink,
                    categoryList = groupedCartProductWithCategoryEntityList.map { menuProductWithCategoryEntity ->
                        Category(
                            uuid = menuProductWithCategoryEntity.categoryUuid,
                            name = menuProductWithCategoryEntity.categoryName,
                            priority = menuProductWithCategoryEntity.priority
                        )
                    },
                    visible = firstCartProductWithCategoryEntity.visible
                )
            )
        }
    }

    override fun toEntityModel(cartProduct: CartProduct): CartProductEntity {
        return CartProductEntity(
            uuid = cartProduct.uuid,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid,
        )
    }
}