package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo

class AddCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepository,
) {

    suspend operator fun invoke(menuProductUuid: String, additionList: List<Addition>): Boolean {
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
            additionList = additionList
        )

        val cartProduct = if (initialCartProduct == null) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)?.also { cartProductUuid ->
                additionList.forEach { addition ->
                    cartProductAdditionRepository.saveAsCartProductAddition(
                        cartProductUuid = cartProductUuid, addition = addition
                    )
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
        additionList: List<Addition>,
    ): CartProduct? {
        return cartProductList.firstOrNull { cartProduct ->
            getIsAdditionsAreEqual(
                initialCartProduct = cartProduct,
                additionList = additionList
            )
        }
    }

    private fun getIsAdditionsAreEqual(
        initialCartProduct: CartProduct?,
        additionList: List<Addition>,
    ): Boolean {
        // Check if the lists have the same size
        val listsHaveSameSize =
            initialCartProduct?.cartProductAdditionList?.size == additionList.size

        // Check if each element in 'additionList' has a corresponding element in 'cartProductAdditionList' with the same 'uuid'
        val listsAreEqual = listsHaveSameSize && additionList.all { addition ->
            initialCartProduct?.cartProductAdditionList?.any { cartProductAddition ->
                cartProductAddition.additionUuid == addition.uuid
            } ?: false
        }
        return listsAreEqual
    }
}