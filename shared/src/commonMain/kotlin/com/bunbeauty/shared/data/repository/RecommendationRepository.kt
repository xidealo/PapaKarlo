package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.repo.RecommendationRepo

class RecommendationRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo
) : CacheRepository<Int>(), RecommendationRepo {
    companion object {
        private const val DEFAULT_RECOMMENDATIONS_COUNT = 6
    }

    override val tag: String = "RECOMMENDATION_TAG"

    override suspend fun getMaxVisibleCount(): Int {
        return getCacheOrData(
            onApiRequest = networkConnector::getRecommendationData,
            onLocalRequest = {
                dataStoreRepo.getRecommendationMaxVisible()
            },
            onSaveLocally = { recommendationMaxVisible ->
                dataStoreRepo.saveRecommendationMaxVisible(recommendationMaxVisible.maxVisibleCount)
            },
            serverToDomainModel = { recommendationProductListServer ->
                recommendationProductListServer.maxVisibleCount
            }
        ) ?: DEFAULT_RECOMMENDATIONS_COUNT
    }
}
