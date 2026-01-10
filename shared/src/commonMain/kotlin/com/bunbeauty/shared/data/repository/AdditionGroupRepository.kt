package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.AdditionGroupRepo
import com.bunbeauty.core.model.addition.AdditionGroup
import com.bunbeauty.shared.data.dao.addition_group.IAdditionGroupDao
import com.bunbeauty.shared.data.mapper.additiongroup.mapAdditionGroupEntityToGroup

class AdditionGroupRepository(
    private val additionGroupDao: IAdditionGroupDao,
) : AdditionGroupRepo {
    override suspend fun getAdditionGroup(uuid: String): AdditionGroup? =
        additionGroupDao.getAdditionGroupEntity(uuid)?.mapAdditionGroupEntityToGroup()
}
