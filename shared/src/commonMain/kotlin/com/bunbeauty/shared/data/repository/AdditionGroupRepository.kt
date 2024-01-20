package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.mapper.additiongroup.mapAdditionGroupEntityToGroup
import com.bunbeauty.shared.data.dao.addition_group.IAdditionGroupDao
import com.bunbeauty.shared.domain.model.addition.AdditionGroup

class AdditionGroupRepository(
    private val additionGroupDao: IAdditionGroupDao,
) {
    suspend fun getAdditionGroup(uuid: String): AdditionGroup? {
        return additionGroupDao.getAdditionGroupEntity(uuid)?.mapAdditionGroupEntityToGroup()
    }
}