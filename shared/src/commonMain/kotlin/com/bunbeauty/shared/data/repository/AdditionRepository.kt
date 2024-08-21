package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.addition.IAdditionDao
import com.bunbeauty.shared.data.mapper.addition.mapAdditionEntityToAddition
import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.repo.AdditionRepo

class AdditionRepository(
    private val additionDao: IAdditionDao
) : AdditionRepo {
    override suspend fun getAddition(uuid: String): Addition? {
        return additionDao.getAdditionEntity(uuid)?.mapAdditionEntityToAddition()
    }
}
