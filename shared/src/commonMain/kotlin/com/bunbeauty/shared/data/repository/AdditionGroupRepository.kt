package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.addition_group.IAdditionGroupDao
import com.bunbeauty.shared.data.mapper.additiongroup.mapAdditionGroupEntityToGroup
import com.bunbeauty.shared.domain.model.addition.AdditionGroup
import com.bunbeauty.shared.domain.repo.AdditionGroupRepo

class AdditionGroupRepository(
    private val additionGroupDao: IAdditionGroupDao,
) : AdditionGroupRepo {
    override suspend fun getAdditionGroup(uuid: String): AdditionGroup? =
        additionGroupDao.getAdditionGroupEntity(uuid)?.mapAdditionGroupEntityToGroup()
}
