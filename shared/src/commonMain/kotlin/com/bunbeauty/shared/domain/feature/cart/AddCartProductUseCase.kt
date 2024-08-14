package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.AdditionGroupRepo
import com.bunbeauty.shared.domain.repo.AdditionRepo
import com.bunbeauty.shared.domain.repo.CartProductAdditionRepo
import com.bunbeauty.shared.domain.repo.CartProductRepo

class AddCartProductUseCase(
    private val getCartProductCountUseCase: GetCartProductCountUseCase,
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepo,
    private val additionRepository: AdditionRepo,
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase,
    private val additionGroupRepository: AdditionGroupRepo,
    private val getAdditionPriorityUseCase: GetAdditionPriorityUseCase
) {
    suspend operator fun invoke(
        menuProductUuid: String,
        additionUuidList: List<String>
    ) {
        if (getCartProductCountUseCase() >= CART_PRODUCT_LIMIT) {
            throw CartProductLimitReachedException()
        }

        val cartProductWithSameAdditions = getCartProductWithSameAdditions(
            cartProductList = cartProductRepo.getCartProductListByMenuProductUuid(
                menuProductUuid
            ),
            additionUuidList = additionUuidList
        )

        if (cartProductWithSameAdditions == null) {
            val cartProductUuid =
                cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid)
            additionUuidList.forEach { additionUuid ->
                additionRepository.getAddition(uuid = additionUuid)?.let { addition ->
                    val additionGroup =
                        additionGroupRepository.getAdditionGroup(uuid = addition.additionGroupUuid)
                    cartProductAdditionRepository.saveAsCartProductAddition(
                        cartProductUuid = cartProductUuid,
                        addition = addition.copy(
                            priority = additionGroup?.let {
                                getAdditionPriorityUseCase(
                                    additionGroup = additionGroup,
                                    addition = addition
                                )
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
        additionUuidList: List<String>
    ): CartProduct? {
        return cartProductList.firstOrNull { cartProduct ->
            areAdditionsEqualUseCase(
                cartProduct = cartProduct,
                additionUuidList = additionUuidList
            )
        }
    }
}
