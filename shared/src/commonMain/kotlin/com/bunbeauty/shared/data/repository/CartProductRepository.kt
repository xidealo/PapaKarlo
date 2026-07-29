package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.CartProductRepo
import com.bunbeauty.core.model.addition.Addition
import com.bunbeauty.core.model.cart.CartProduct
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.data.dao.cart_product.ICartProductDao
import com.bunbeauty.shared.data.dao.menu_product.IMenuProductDao
import com.bunbeauty.shared.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.shared.db.CartProductAdditionEntity
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.MenuProductWithCategoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartProductRepository(
    private val uuidGenerator: UuidGenerator,
    private val foodDeliveryDatabase: FoodDeliveryDatabase,
    private val cartProductDao: ICartProductDao,
    private val menuProductDao: IMenuProductDao,
    private val cartProductMapper: ICartProductMapper,
) : CartProductRepo {
    private val cartProductEntityQueries = foodDeliveryDatabase.cartProductEntityQueries
    private val cartProductAdditionEntityQueries = foodDeliveryDatabase.cartProductAdditionEntityQueries

    override fun observeCartProductList(): Flow<List<CartProduct>> =
        cartProductDao.observeCartProductList().map { cartProductList ->
            cartProductList.toCartProductList()
        }

    override suspend fun getCartProductList(): List<CartProduct> = cartProductDao.getCartProductList().toCartProductList()

    override suspend fun getCartProductCount(): Int = cartProductDao.getCartProductCount()

    override suspend fun getCartProduct(cartProductUuid: String): CartProduct? =
        cartProductDao
            .getCartProductByUuid(cartProductUuid)
            .toCartProductList()
            .firstOrNull()

    override suspend fun getCartProductListByMenuProductUuid(menuProductUuid: String): List<CartProduct> =
        cartProductDao
            .getCartProductByMenuProductUuid(menuProductUuid)
            .toCartProductList()

    override suspend fun saveAsCartProduct(menuProductUuid: String): String {
        val uuid = uuidGenerator.generateUuid()
        val cartProductEntity =
            CartProductEntity(
                uuid = uuid,
                count = 1,
                menuProductUuid = menuProductUuid,
            )
        cartProductDao.insertCartProduct(cartProductEntity)

        return uuid
    }

    override suspend fun saveCartProductWithAdditions(
        menuProductUuid: String,
        additions: List<Addition>,
    ): String {
        val cartProductUuid = uuidGenerator.generateUuid()
        val cartProductEntity =
            CartProductEntity(
                uuid = cartProductUuid,
                count = 1,
                menuProductUuid = menuProductUuid,
            )
        val cartProductAdditionEntityList =
            additions.map { addition ->
                addition.toCartProductAdditionEntity(cartProductUuid = cartProductUuid)
            }

        cartProductEntityQueries.transaction {
            cartProductEntityQueries.insertCartProduct(
                uuid = cartProductEntity.uuid,
                count = cartProductEntity.count,
                menuProductUuid = cartProductEntity.menuProductUuid,
            )
            cartProductAdditionEntityList.forEach { cartProductAdditionEntity ->
                cartProductAdditionEntityQueries.insertCartProductAdditionEntity(cartProductAdditionEntity)
            }
        }

        return cartProductUuid
    }

    override suspend fun updateCartProductCount(
        cartProductUuid: String,
        count: Int,
    ) {
        cartProductDao.updateCartProductCountByUuid(cartProductUuid, count)
    }

    override suspend fun deleteCartProduct(cartProductUuid: String) {
        cartProductDao.deleteCartProductByUuid(cartProductUuid)
    }

    override suspend fun deleteAllCartProducts() {
        cartProductDao.deleteAllCartProducts()
    }

    private suspend fun List<CartProductWithMenuProductEntity>.toCartProductList(): List<CartProduct> {
        val visibleCartProductList =
            filter { cartProductWithMenuProductEntity ->
                cartProductWithMenuProductEntity.visible
            }
        val menuProductUuidList =
            visibleCartProductList
                .map { cartProductWithMenuProductEntity ->
                    cartProductWithMenuProductEntity.uuid
                }.distinct()
        val menuProductWithCategoryEntityListByUuid =
            if (menuProductUuidList.isEmpty()) {
                emptyMap()
            } else {
                menuProductDao
                    .getMenuProductWithCategoryListByUuidList(menuProductUuidList)
                    .groupBy(MenuProductWithCategoryEntity::uuid)
            }

        return visibleCartProductList
            .groupBy { cartProductWithMenuProductEntity ->
                cartProductWithMenuProductEntity.cartProductUuid
            }.map { (_, cartProductWithMenuProductEntityList) ->
                val menuProductUuid = cartProductWithMenuProductEntityList.first().uuid

                cartProductMapper.toCartProduct(
                    cartProductWithMenuProductEntityList = cartProductWithMenuProductEntityList,
                    menuProductWithCategoryEntityList =
                        menuProductWithCategoryEntityListByUuid[menuProductUuid].orEmpty(),
                )
            }
    }

    private fun Addition.toCartProductAdditionEntity(cartProductUuid: String): CartProductAdditionEntity =
        CartProductAdditionEntity(
            uuid = uuidGenerator.generateUuid(),
            name = name,
            price = price,
            additionUuid = uuid,
            cartProductUuid = cartProductUuid,
            fullName = fullName,
            priority = priority,
        )
}
