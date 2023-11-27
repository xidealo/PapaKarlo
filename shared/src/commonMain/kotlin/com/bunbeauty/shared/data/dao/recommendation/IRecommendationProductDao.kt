package com.bunbeauty.shared.data.dao.recommendation

import com.bunbeauty.shared.db.RecommendationProductEntity
import com.bunbeauty.shared.db.RecommendationProductWithMenuProductEntity

interface IRecommendationProductDao {
    suspend fun insertList(recommendationProductEntityList: List<RecommendationProductEntity>)
    suspend fun getRecommendationProductWithMenuProductList(): List<RecommendationProductWithMenuProductEntity>
}