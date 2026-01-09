package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.dao.cafe.ICafeDao
import com.bunbeauty.shared.data.mapper.cafe.toCafe
import com.bunbeauty.shared.data.mapper.cafe.toCafeEntity
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.storage.CafeStorage
import com.bunbeauty.shared.db.SelectedCafeUuidEntity
import com.bunbeauty.shared.domain.repo.CafeRepo
import com.bunbeauty.core.extension.dataOrNull
import com.bunbeauty.core.model.cafe.Cafe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CafeRepository(
    private val networkConnector: NetworkConnector,
    private val cafeDao: ICafeDao,
    private val cafeStorage: CafeStorage,
    private val dataStoreRepo: DataStoreRepo,
) : CafeRepo {
    private var cafeListCache: List<Cafe>? = null

    override suspend fun getCafeList(selectedCityUuid: String): List<Cafe> {
        val list =
            if (cafeListCache == null) {
                val cafeList =
                    networkConnector
                        .getCafeListByCityUuid(cityUuid = selectedCityUuid)
                        .dataOrNull()
                        ?.results
                        .also { cafeServerList ->
                            if (cafeServerList != null) {
                                withContext(Dispatchers.IO) {
                                    cafeDao.insertCafeList(
                                        cafeServerList.map { cafeServer ->
                                            cafeServer.toCafeEntity()
                                        },
                                    )
                                }
                            }
                        }?.map { cafeServer ->
                            cafeServer.toCafe()
                        } ?: cafeDao
                        .getCafeListByCityUuid(cityUuid = selectedCityUuid)
                        .map { cafeEntity ->
                            cafeEntity.toCafe()
                        }

                cafeListCache = cafeList

                cafeList
            } else {
                cafeListCache
            }

        return list ?: emptyList()
    }

    override suspend fun saveSelectedCafeUuid(
        userUuid: String,
        selectedCityUuid: String,
        cafeUuid: String,
    ) {
        val selectedCafeUuidEntity =
            SelectedCafeUuidEntity(
                userUuid = userUuid,
                cityUuid = selectedCityUuid,
                cafeUuid = cafeUuid,
            )
        cafeDao.insertSelectedCafeUuid(selectedCafeUuidEntity)
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? =
        getCafeList(
            selectedCityUuid = dataStoreRepo.getSelectedCityUuid().orEmpty(),
        ).find { cafe ->
            cafe.uuid == cafeUuid
        }

    override suspend fun getSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Cafe? = cafeDao.getSelectedCafeByUserAndCityUuid(userUuid, cityUuid)?.toCafe()

    override fun clearCache() {
        cafeStorage.clear()
        cafeListCache = null
    }
}
