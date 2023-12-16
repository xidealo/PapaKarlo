package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo

class AddCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepository,
    private val additionRepository: AdditionRepository,
) {

    suspend operator fun invoke(
        menuProductUuid: String,
        additionUuidList: List<String>,
    ): Boolean {
        val cartCount = cartProductRepo.getCartProductList().sumOf { cartProduct ->
            cartProduct.count
        }
        if (cartCount >= CART_PRODUCT_LIMIT) {
            return false
        }

        val initialCartProduct = getCartProductWithSameAdditions(
            cartProductList = cartProductRepo.getCartProductListByMenuProductUuid(
                menuProductUuid,
            ),
            additionUuidList = additionUuidList
        )

        val cartProduct = if (initialCartProduct == null) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)?.also { cartProductUuid ->
                additionUuidList.forEach { additionUuid ->
                    additionRepository.getAddition(uuid = additionUuid)
                        ?.let { foundAddition ->
                            cartProductAdditionRepository.saveAsCartProductAddition(
                                cartProductUuid = cartProductUuid,
                                addition = foundAddition
                            )
                        }
                }
            }
        } else {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = initialCartProduct.uuid,
                count = initialCartProduct.count + 1
            )
        }
        return cartProduct != null
    }

    private fun getCartProductWithSameAdditions(
        cartProductList: List<CartProduct>,
        additionUuidList: List<String>,
    ): CartProduct? {
        return cartProductList.firstOrNull { cartProduct ->
            getIsAdditionsAreEqual(
                initialCartProduct = cartProduct,
                additionList = additionUuidList
            )
        }
    }

    private fun getIsAdditionsAreEqual(
        initialCartProduct: CartProduct?,
        additionList: List<String>,
    ): Boolean {
        // Check if the lists have the same size
        val listsHaveSameSize =
            initialCartProduct?.cartProductAdditionList?.size == additionList.size

        // Check if each element in 'additionList' has a corresponding element in 'cartProductAdditionList' with the same 'uuid'
        val listsAreEqual = listsHaveSameSize && additionList.all { additionUuid ->
            initialCartProduct?.cartProductAdditionList?.any { cartProductAddition ->
                cartProductAddition.additionUuid == additionUuid
            } ?: false
        }
        return listsAreEqual
    }
}