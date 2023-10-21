package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.recommendation.IRecommendationProductDao
import com.bunbeauty.shared.data.mapper.RecommendationMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.RecommendationProduct

class RecommendationRepository(
    private val networkConnector: NetworkConnector,
    private val recommendationMapper: RecommendationMapper,
    private val recommendationProductDao: IRecommendationProductDao,
) : CacheListRepository<RecommendationProduct>() {

    override val tag: String = "RECOMMENDATION_TAG"

    suspend fun getRecommendations(): List<RecommendationProduct> {
        return getCacheOrListData(
            onApiRequest = networkConnector::getRecommendationList,
            onLocalRequest = {
                recommendationProductDao.getRecommendationProductWithMenuProductList()
                    .map(recommendationMapper::toRecommendationProduct)
            },
            onSaveLocally = { recommendationProductList ->
                recommendationProductDao.insertList(
                    recommendationProductList.map(
                        recommendationMapper::toRecommendationProductEntity
                    )
                )
            },
            serverToDomainModel = recommendationMapper::toRecommendation
        )
    }
}