package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.data.repository.AdditionGroupRepository
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.GetIsAdditionsAreEqualUseCase
import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.model.addition.AdditionGroup
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo

//todo (add tests for calculating priority)

class AddCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepository,
    private val additionRepository: AdditionRepository,
    private val getIsAdditionsAreEqualUseCase: GetIsAdditionsAreEqualUseCase,
    private val additionGroupRepository: AdditionGroupRepository,
) {

    companion object {
        private const val ADDITION_GROUP_COEFFICIENT = 10
    }

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
                            val additionGroup =
                                additionGroupRepository.getAdditionGroup(foundAddition.additionGroupUuid)
                            cartProductAdditionRepository.saveAsCartProductAddition(
                                cartProductUuid = cartProductUuid,
                                addition = foundAddition.copy(
                                    priority = getAdditionPriority(additionGroup, foundAddition)
                                )
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

    private fun getAdditionPriority(
        additionGroup: AdditionGroup?,
        foundAddition: Addition,
    ) = (additionGroup?.priority ?: 0) * ADDITION_GROUP_COEFFICIENT + foundAddition.priority


    private fun getCartProductWithSameAdditions(
        cartProductList: List<CartProduct>,
        additionUuidList: List<String>,
    ): CartProduct? {
        return cartProductList.firstOrNull { cartProduct ->
            getIsAdditionsAreEqualUseCase(
                initialCartProduct = cartProduct,
                additionUuidList = additionUuidList
            )
        }
    }
}