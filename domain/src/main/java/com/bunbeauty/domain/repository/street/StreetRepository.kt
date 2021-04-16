package com.bunbeauty.domain.repository.street

import com.bunbeauty.data.StreetDao
import com.bunbeauty.data.model.Street
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