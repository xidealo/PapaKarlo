package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.network.api.NetworkConnector

class RecommendationRepository(
    private val networkConnector: NetworkConnector,
) : CacheRepository<Int>() {
    companion object {
        private const val DEFAULT_RECOMMENDATIONS_COUNT = 6
    }

    override val tag: String = "RECOMMENDATION_TAG"

    suspend fun getMaxVisibleCount(): Int {
        return getCacheOrData(
            onApiRequest = networkConnector::getRecommendationData,
            onLocalRequest = {
                DEFAULT_RECOMMENDATIONS_COUNT
            },
            onSaveLocally = {
                //todo save to datastore
            },
            serverToDomainModel = { recommendationProductListServer ->
                recommendationProductListServer.maxVisibleCount
            }
        ) ?: DEFAULT_RECOMMENDATIONS_COUNT
    }
}