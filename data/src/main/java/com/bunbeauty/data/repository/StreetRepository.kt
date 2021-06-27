package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.StreetDao
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.repo.StreetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreetRepository @Inject constructor(
    private val streetDao: StreetDao
) : StreetRepo {

    override suspend fun insert(street: Street): Long {
        return streetDao.insert(street)
    }

    override fun getStreets(): Flow<List<Street>> {
        return streetDao.getStreets()
    }
}