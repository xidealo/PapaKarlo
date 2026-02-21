package com.bunbeauty.core.domain.cart

import com.bunbeauty.core.model.cart.CartProduct
import com.bunbeauty.core.model.category.Category
import com.bunbeauty.core.model.product.MenuProduct
import com.bunbeauty.core.domain.menu_product.GetMenuProductListUseCase
import com.bunbeauty.core.domain.repo.CartProductRepo
import com.bunbeauty.core.domain.repo.RecommendationRepo

/*
* 1 - Берем список рекомендаций
* 2 - Берем только те, категорий которых нет в корзине
* 3 - Если рекомендаций меньше 2-х, берем те, которых нет в корзине
* 4 - Рекомендуем первые 4 (maxVisibleCount)
* */
class GetRecommendationsUseCase(
    private val recommendationRepository: RecommendationRepo,
    private val cartProductRepo: CartProductRepo,
    private val getMenuProductListUseCase: GetMenuProductListUseCase,
) {
    suspend operator fun invoke(): List<MenuProduct> {
        val cartProductList = cartProductRepo.getCartProductList()
        val maxVisibleCount = recommendationRepository.getMaxVisibleCount()

        val recommendationProductList =
            getMenuProductListUseCase().filter { menuProduct ->
                menuProduct.isRecommended
            }

        val recommendationProductListNoneCategory =
            getFilteredRecommendationsByCategories(
                recommendationProductList = recommendationProductList,
                cartProductList = cartProductList,
            )

        return if (recommendationProductListNoneCategory.size < 2) {
            recommendationProductListNoneCategory +
                getRecommendationsByCartAndFilteredRecommendations(
                    recommendationProductList = recommendationProductList,
                    cartProductList = cartProductList,
                    recommendationProductListNoneCategory = recommendationProductListNoneCategory,
                )
        } else {
            recommendationProductListNoneCategory
        }.take(maxVisibleCount)
    }

    private fun getFilteredRecommendationsByCategories(
        recommendationProductList: List<MenuProduct>,
        cartProductList: List<CartProduct>,
    ) = recommendationProductList.filterNot { recommendationProduct ->
        hasRecommendationCategoriesInCart(recommendationProduct, cartProductList)
    }

    private fun getRecommendationsByCartAndFilteredRecommendations(
        recommendationProductList: List<MenuProduct>,
        cartProductList: List<CartProduct>,
        recommendationProductListNoneCategory: List<MenuProduct>,
    ) = recommendationProductList.filter { recommendationProduct ->
        cartProductList.none {
            it.product.uuid == recommendationProduct.uuid
        } &&
            recommendationProductListNoneCategory.none {
                it.uuid == recommendationProduct.uuid
            }
    }

    private fun hasRecommendationCategoriesInCart(
        recommendationProduct: MenuProduct,
        cartProductList: List<CartProduct>,
    ) = recommendationProduct.categoryList.any { recommendationProductCategory ->
        hasRecommendationCategoriesInCartProduct(cartProductList, recommendationProductCategory)
    }

    private fun hasRecommendationCategoriesInCartProduct(
        cartProductList: List<CartProduct>,
        recommendationProductCategory: Category,
    ) = cartProductList.any { cartProduct ->
        hasCategoryInCartProduct(cartProduct, recommendationProductCategory)
    }

    private fun hasCategoryInCartProduct(
        cartProduct: CartProduct,
        recommendationProductCategory: Category,
    ) = cartProduct.product.categoryList.any { cartProductCategory ->
        cartProductCategory.uuid == recommendationProductCategory.uuid
    }
}
