package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.StreetDao
import com.bunbeauty.domain.mapper.IStreetMapper
import com.bunbeauty.domain.model.ui.Street
import com.bunbeauty.domain.repo.StreetRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StreetRepository @Inject constructor(
    private val streetDao: StreetDao,
    private val streetMapper: IStreetMapper,
) : StreetRepo {

    override suspend fun getStreets(): List<Street> {
        return streetDao.getStreetList().map(streetMapper::toUIModel)
    }
}