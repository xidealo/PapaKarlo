package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.category.Category

class GetRecommendationsUseCase(
    private val recommendationRepository: RecommendationRepository,
    private val cartProductInteractor: ICartProductInteractor,
) {
    suspend operator fun invoke(): List<RecommendationProduct> {

        val cartProductList = cartProductInteractor.getCartProductList()
        val recommendationsWithMaxCount = recommendationRepository.getRecommendations()

        val recommendationProductList =
            recommendationsWithMaxCount?.recommendationProductList ?: return emptyList()

        val recommendationProductListNoneCategory = getFilteredRecommendationsByCategories(
            recommendationProductList = recommendationProductList,
            cartProductList = cartProductList
        )

        return if (recommendationProductListNoneCategory.size < 2) {
            recommendationProductListNoneCategory + getRecommendationsByCartAndFilteredRecommendations(
                recommendationProductList = recommendationProductList,
                cartProductList = cartProductList,
                recommendationProductListNoneCategory = recommendationProductListNoneCategory
            )
        } else {
            recommendationProductListNoneCategory
        }.take(recommendationsWithMaxCount.maxVisibleCount)
    }

    private fun getFilteredRecommendationsByCategories(
        recommendationProductList: List<RecommendationProduct>,
        cartProductList: List<CartProduct>,
    ) = recommendationProductList.filterNot { recommendationProduct ->
        hasRecommendationCategoriesInCart(recommendationProduct, cartProductList)
    }

    private fun getRecommendationsByCartAndFilteredRecommendations(
        recommendationProductList: List<RecommendationProduct>,
        cartProductList: List<CartProduct>,
        recommendationProductListNoneCategory: List<RecommendationProduct>,
    ) = recommendationProductList.filter { recommendationProduct ->
        cartProductList.none {
            it.product.uuid == recommendationProduct.menuProduct.uuid
        } && recommendationProductListNoneCategory.none {
            it.menuProduct.uuid == recommendationProduct.menuProduct.uuid
        }
    }

    private fun hasRecommendationCategoriesInCart(
        recommendationProduct: RecommendationProduct,
        cartProductList: List<CartProduct>,
    ) = recommendationProduct.menuProduct.categoryList.any { recommendationProductCategory ->
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