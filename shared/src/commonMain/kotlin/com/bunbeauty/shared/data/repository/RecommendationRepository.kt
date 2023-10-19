package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.mapper.menuProduct.IMenuProductMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.RecommendationServer
import com.bunbeauty.shared.domain.model.Recommendation

class RecommendationRepository(
    private val networkConnector: NetworkConnector,
    private val menuProductMapper: IMenuProductMapper,
) : CacheListRepository<Recommendation>() {

    override val tag: String = "RECOMMENDATION_TAG"

    suspend fun getRecommendations(): List<Recommendation> {
        return getCacheOrListData(
            onApiRequest = networkConnector::getRecommendationList,
            onLocalRequest = {
                emptyList()
                /*menuProductMapper.toMenuProductList(
                    menuProductDao.getMenuProductWithCategoryList()
                )*/
            },
            onSaveLocally = { },
            serverToDomainModel = ::toRecommendation
        )
    }

    private fun toRecommendation(recommendationServer: RecommendationServer): Recommendation {
        return Recommendation(
            uuid = recommendationServer.uuid,
            menuProduct = menuProductMapper.toMenuProduct(recommendationServer.menuProduct)
        )
    }
}