package com.bunbeauty.shared.data.mapper

import com.bunbeauty.shared.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.shared.data.network.model.RecommendationProductServer
import com.bunbeauty.shared.db.RecommendationProductEntity
import com.bunbeauty.shared.db.RecommendationProductWithMenuProductEntity
import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.product.MenuProduct

class RecommendationMapper(
    private val menuProductMapper: IMenuProductMapper,
) {
    fun toRecommendationProductEntity(recommendationProduct: RecommendationProduct): RecommendationProductEntity {
        return RecommendationProductEntity(
            uuid = recommendationProduct.uuid,
            menuProductUuid = recommendationProduct.menuProduct.uuid
        )
    }
    fun toRecommendationProductEntity(recommendationProductServer: RecommendationProductServer): RecommendationProductEntity {
        return RecommendationProductEntity(
            uuid = recommendationProductServer.uuid,
            menuProductUuid = recommendationProductServer.menuProduct.uuid
        )
    }

    fun toRecommendationProduct(recommendationProductWithMenuProductEntity: RecommendationProductWithMenuProductEntity): RecommendationProduct {
        return RecommendationProduct(
            uuid = recommendationProductWithMenuProductEntity.uuid,
            menuProduct = MenuProduct(
                uuid = recommendationProductWithMenuProductEntity.uuid,
                name = recommendationProductWithMenuProductEntity.name,
                newPrice = recommendationProductWithMenuProductEntity.newPrice,
                oldPrice = recommendationProductWithMenuProductEntity.oldPrice,
                utils = recommendationProductWithMenuProductEntity.utils,
                nutrition = recommendationProductWithMenuProductEntity.nutrition,
                description = recommendationProductWithMenuProductEntity.description,
                comboDescription = recommendationProductWithMenuProductEntity.comboDescription,
                photoLink = recommendationProductWithMenuProductEntity.photoLink,
                categoryList = emptyList(),
                visible = recommendationProductWithMenuProductEntity.visible
            )
        )
    }

    fun toRecommendation(recommendationProductServer: RecommendationProductServer): RecommendationProduct {
        return RecommendationProduct(
            uuid = recommendationProductServer.uuid,
            menuProduct = menuProductMapper.toMenuProduct(recommendationProductServer.menuProduct)
        )
    }
}