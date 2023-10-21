package com.bunbeauty.shared.data.dao.recommendation

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.RecommendationProductEntity
import com.bunbeauty.shared.db.RecommendationProductWithMenuProductEntity

class RecommendationProductDao(foodDeliveryDatabase: FoodDeliveryDatabase) :
    IRecommendationProductDao {

    private val recommendationEntityQueries = foodDeliveryDatabase.recommendationEntityQueries

    override suspend fun insertList(recommendationProductEntityList: List<RecommendationProductEntity>) {
        recommendationProductEntityList.forEach { recommendationProductEntity ->
            recommendationEntityQueries.insertRecommendationProduct(
                uuid = recommendationProductEntity.uuid,
                menuProductUuid = recommendationProductEntity.menuProductUuid
            )
        }
    }

    override suspend fun getRecommendationProductWithMenuProductList(): List<RecommendationProductWithMenuProductEntity> {
        return recommendationEntityQueries.getRecommendationProductList().executeAsList()
    }

}