package com.bunbeauty.core.domain.cart

import com.bunbeauty.core.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.core.domain.GetCartProductCountUseCase
import com.bunbeauty.core.domain.addition.AreAdditionsEqualUseCase
import com.bunbeauty.core.domain.addition.GetAdditionPriorityUseCase
import com.bunbeauty.core.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.core.domain.repo.AdditionGroupRepo
import com.bunbeauty.core.domain.repo.AdditionRepo
import com.bunbeauty.core.domain.repo.CartProductRepo
import com.bunbeauty.core.model.addition.Addition
import com.bunbeauty.core.model.cart.CartProduct

class AddCartProductUseCase(
    private val getCartProductCountUseCase: GetCartProductCountUseCase,
    private val cartProductRepo: CartProductRepo,
    private val additionRepository: AdditionRepo,
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase,
    private val additionGroupRepository: AdditionGroupRepo,
    private val getAdditionPriorityUseCase: GetAdditionPriorityUseCase,
) {
    suspend operator fun invoke(
        menuProductUuid: String,
        additionUuidList: List<String>,
    ) {
        if (getCartProductCountUseCase() >= CART_PRODUCT_LIMIT) {
            throw CartProductLimitReachedException()
        }

        val cartProductWithSameAdditions =
            getCartProductWithSameAdditions(
                cartProductList =
                    cartProductRepo.getCartProductListByMenuProductUuid(
                        menuProductUuid,
                    ),
                additionUuidList = additionUuidList,
            )

        if (cartProductWithSameAdditions == null) {
            cartProductRepo.saveCartProductWithAdditions(
                menuProductUuid = menuProductUuid,
                additions = buildAdditions(additionUuidList = additionUuidList),
            )
        } else {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductWithSameAdditions.uuid,
                count = cartProductWithSameAdditions.count + 1,
            )
        }
    }

    private suspend fun buildAdditions(additionUuidList: List<String>): List<Addition> =
        additionUuidList.mapNotNull { additionUuid ->
            additionRepository.getAddition(uuid = additionUuid)?.let { addition ->
                val additionGroup =
                    additionGroupRepository.getAdditionGroup(uuid = addition.additionGroupUuid)
                addition.copy(
                    priority =
                        additionGroup?.let {
                            getAdditionPriorityUseCase(
                                additionGroup = additionGroup,
                                addition = addition,
                            )
                        } ?: addition.priority,
                )
            }
        }

    private fun getCartProductWithSameAdditions(
        cartProductList: List<CartProduct>,
        additionUuidList: List<String>,
    ): CartProduct? =
        cartProductList.firstOrNull { cartProduct ->
            areAdditionsEqualUseCase(
                cartProduct = cartProduct,
                additionUuidList = additionUuidList,
            )
        }
}
