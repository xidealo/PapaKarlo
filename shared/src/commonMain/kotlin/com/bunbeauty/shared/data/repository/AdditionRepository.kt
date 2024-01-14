package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.addition.IAdditionDao
import com.bunbeauty.shared.domain.model.addition.Addition

class AdditionRepository(
    private val additionDao: IAdditionDao,
) {
    suspend fun getAddition(uuid: String): Addition? {
        return additionDao.getAdditionEntity(uuid)?.let { additionEntity ->
            Addition(
                isSelected = additionEntity.isSelected,
                isVisible = additionEntity.isVisible,
                name = additionEntity.name,
                photoLink = additionEntity.photoLink,
                price = additionEntity.price,
                uuid = additionEntity.uuid,
                additionGroupServer = additionEntity.additionGroupUuid,
                fullName = additionEntity.fullName,
                priority = additionEntity.priority
            )
        }
    }
}