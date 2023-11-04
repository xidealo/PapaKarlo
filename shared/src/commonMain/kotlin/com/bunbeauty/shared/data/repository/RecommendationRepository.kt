package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.recommendation.IRecommendationProductDao
import com.bunbeauty.shared.data.mapper.RecommendationMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.RecommendationProductListServer
import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.RecommendationProductList

class RecommendationRepository(
    private val networkConnector: NetworkConnector,
    private val recommendationMapper: RecommendationMapper,
    private val recommendationProductDao: IRecommendationProductDao,
) : CacheRepository<RecommendationProductList>() {

    override val tag: String = "RECOMMENDATION_TAG"

    suspend fun getRecommendations(): RecommendationProductList? {
        return getCacheOrData(
            onApiRequest = networkConnector::getRecommendationList,
            onLocalRequest = {
                val list = recommendationMapper
                    .toRecommendationProductList(recommendationProductDao.getRecommendationProductWithMenuProductList())
                RecommendationProductList(maxVisibleCount = 6, recommendationProductList = list)
            },
            onSaveLocally = { recommendationProductList ->
                recommendationProductDao.insertList(
                    recommendationProductList.recommendationList.map(
                        recommendationMapper::toRecommendationProductEntity
                    )
                )
            },
            serverToDomainModel = recommendationMapper::toRecommendation
        )
    }
}