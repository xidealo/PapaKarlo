package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.data.repository.AdditionGroupRepository
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo

class AddCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepository,
    private val additionRepository: AdditionRepository,
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase,
    private val additionGroupRepository: AdditionGroupRepository,
    private val getAdditionPriorityUseCase: GetAdditionPriorityUseCase,
) {
    suspend operator fun invoke(
        menuProductUuid: String,
        additionUuidList: List<String>,
    ) {
        val cartCount = cartProductRepo.getCartProductList().sumOf { cartProduct ->
            cartProduct.count
        }
        if (cartCount >= CART_PRODUCT_LIMIT) {
            return
        }

        val cartProductWithSameAdditions = getCartProductWithSameAdditions(
            cartProductList = cartProductRepo.getCartProductListByMenuProductUuid(
                menuProductUuid,
            ),
            additionUuidList = additionUuidList
        )

        if (cartProductWithSameAdditions == null) {
            val cartProductUuid = cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid)
            additionUuidList.forEach { additionUuid ->
                additionRepository.getAddition(uuid = additionUuid)?.let { addition ->
                    val additionGroup = additionGroupRepository.getAdditionGroup(uuid = addition.additionGroupUuid)
                    cartProductAdditionRepository.saveAsCartProductAddition(
                        cartProductUuid = cartProductUuid,
                        addition = addition.copy(
                            priority = additionGroup?.let {
                                getAdditionPriorityUseCase(additionGroup = additionGroup, addition = addition)
                            } ?: addition.priority
                        )
                    )
                }
            }
        } else {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductWithSameAdditions.uuid,
                count = cartProductWithSameAdditions.count + 1
            )
        }
    }

    private fun getCartProductWithSameAdditions(
        cartProductList: List<CartProduct>,
        additionUuidList: List<String>,
    ): CartProduct? {
        return cartProductList.firstOrNull { cartProduct ->
            areAdditionsEqualUseCase(
                cartProduct = cartProduct,
                additionUuidList = additionUuidList
            )
        }
    }
}