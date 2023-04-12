package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.street.IStreetDao
import com.bunbeauty.shared.data.mapper.street.IStreetMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.domain.model.street.Street
import com.bunbeauty.shared.domain.model.street.StreetCache
import com.bunbeauty.shared.domain.repo.StreetRepo

class StreetRepository(
    private val networkConnector: NetworkConnector,
    private val streetDao: IStreetDao,
    private val streetMapper: IStreetMapper,
) : BaseRepository(), StreetRepo {

    override val tag: String = "STREET_TAG"

    private var streetCache: StreetCache? = null

    override suspend fun getStreetList(userUuid: String, cityUuid: String): List<Street> {
        val cache = streetCache
        return if (cache != null
            && cache.userUuid == userUuid
            && cache.cityUuid == cityUuid
        ) {
            cache.streetList
        } else {
            networkConnector.getStreetListByCityUuid(cityUuid).getListResult(
                onError = {
                    streetDao.getStreetListByCityUuid(cityUuid).map(streetMapper::toStreet)
                },
                onSuccess = { streetServerList ->
                    streetDao.insertStreetList(streetServerList.map(streetMapper::toStreetEntity))
                    streetServerList.map { serverModel ->
                        streetMapper.toStreet(serverModel)
                    }.also { streetList ->
                        streetCache = StreetCache(
                            streetList = streetList,
                            userUuid = userUuid,
                            cityUuid = cityUuid
                        )
                    }
                }
            )
        }
    }

    override suspend fun getStreetByNameAndCityUuid(name: String, cityUuid: String): Street? {
        return streetDao.getStreetByNameAndCityUuid(name, cityUuid)?.let { streetEntity ->
            streetMapper.toStreet(streetEntity)
        }
    }
}