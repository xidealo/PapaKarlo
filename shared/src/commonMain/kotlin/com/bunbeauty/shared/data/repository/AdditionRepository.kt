package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.addition.IAdditionDao
import com.bunbeauty.shared.data.mapper.addition.mapAdditionEntityToAddition
import com.bunbeauty.shared.domain.model.addition.Addition

class AdditionRepository(
    private val additionDao: IAdditionDao
) {
    suspend fun getAddition(uuid: String): Addition? {
        return additionDao.getAdditionEntity(uuid)?.mapAdditionEntityToAddition()
    }
}
