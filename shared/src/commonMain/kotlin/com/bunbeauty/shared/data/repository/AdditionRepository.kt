package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.AdditionRepo
import com.bunbeauty.core.model.addition.Addition
import com.bunbeauty.shared.data.dao.addition.IAdditionDao
import com.bunbeauty.shared.data.mapper.addition.mapAdditionEntityToAddition

class AdditionRepository(
    private val additionDao: IAdditionDao,
) : AdditionRepo {
    override suspend fun getAddition(uuid: String): Addition? = additionDao.getAdditionEntity(uuid)?.mapAdditionEntityToAddition()
}
