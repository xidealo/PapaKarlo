package com.bunbeauty.shared.data.mapper

import com.bunbeauty.shared.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.shared.data.network.model.RecommendationProductListServer
import com.bunbeauty.shared.data.network.model.RecommendationProductServer
import com.bunbeauty.shared.db.RecommendationProductEntity
import com.bunbeauty.shared.db.RecommendationProductWithMenuProductEntity
import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.RecommendationProductList
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct

class RecommendationMapper(
    private val menuProductMapper: IMenuProductMapper,
) {
    fun toRecommendationProductEntity(recommendationProduct: RecommendationProduct): RecommendationProductEntity {
        return RecommendationProductEntity(
            uuid = recommendationProduct.uuid,
            menuProductUuid = recommendationProduct.menuProduct.uuid,
            isVisible = recommendationProduct.isVisible
        )
    }

    fun toRecommendationProductEntity(recommendationProductServer: RecommendationProductServer): RecommendationProductEntity {
        return RecommendationProductEntity(
            uuid = recommendationProductServer.uuid,
            menuProductUuid = recommendationProductServer.menuProduct.uuid,
            isVisible = recommendationProductServer.isVisible
        )
    }

    fun toRecommendationProductList(recommendationProductWithMenuProductEntityList: List<RecommendationProductWithMenuProductEntity>): List<RecommendationProduct> {
        return recommendationProductWithMenuProductEntityList.groupBy { recommendationProductWithMenuProductEntity ->
            recommendationProductWithMenuProductEntity.menuProductUuid
        }.map { (_, groupedRecommendationProductWithCategoryEntityList) ->
            val firstRecommendationProductWithCategoryEntity =
                groupedRecommendationProductWithCategoryEntityList.first()
            RecommendationProduct(
                uuid = firstRecommendationProductWithCategoryEntity.recommendationProductUuid,
                menuProduct = MenuProduct(
                    uuid = firstRecommendationProductWithCategoryEntity.menuProductUuid,
                    name = firstRecommendationProductWithCategoryEntity.name,
                    newPrice = firstRecommendationProductWithCategoryEntity.newPrice,
                    oldPrice = firstRecommendationProductWithCategoryEntity.oldPrice,
                    utils = firstRecommendationProductWithCategoryEntity.utils,
                    nutrition = firstRecommendationProductWithCategoryEntity.nutrition,
                    description = firstRecommendationProductWithCategoryEntity.description,
                    comboDescription = firstRecommendationProductWithCategoryEntity.comboDescription,
                    photoLink = firstRecommendationProductWithCategoryEntity.photoLink,
                    categoryList = groupedRecommendationProductWithCategoryEntityList.map { menuProductWithCategoryEntity ->
                        Category(
                            uuid = menuProductWithCategoryEntity.categoryUuid,
                            name = menuProductWithCategoryEntity.categoryName,
                            priority = menuProductWithCategoryEntity.priority
                        )
                    },
                    visible = firstRecommendationProductWithCategoryEntity.visible,
                ),
                isVisible = false
            )
        }
    }

    fun toRecommendation(recommendationProductListServer: RecommendationProductListServer): RecommendationProductList {
        return RecommendationProductList(
            maxVisibleCount = recommendationProductListServer.maxVisibleCount,
            recommendationProductList = recommendationProductListServer.recommendationList.map { recommendationProductServer ->
                RecommendationProduct(
                    uuid = recommendationProductServer.uuid,
                    menuProduct = menuProductMapper.toMenuProduct(recommendationProductServer.menuProduct),
                    isVisible = recommendationProductServer.isVisible
                )
            }
        )
    }
}