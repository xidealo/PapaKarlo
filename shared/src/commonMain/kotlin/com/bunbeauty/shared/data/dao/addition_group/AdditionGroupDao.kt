package com.bunbeauty.shared.data.dao.addition_group

import com.bunbeauty.shared.db.AdditionGroupEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase

class AdditionGroupDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IAdditionGroupDao {

    private val additionGroupEntityQueries = foodDeliveryDatabase.additionGroupEntityQueries

    override suspend fun insertList(additionGroupEntities: List<AdditionGroupEntity>) {
        additionGroupEntities.forEach { additionGroupEntity ->
            additionGroupEntityQueries.insert(additionGroupEntity)
        }
    }

    override suspend fun getAdditionGroupEntity(uuid: String): AdditionGroupEntity? {
        return additionGroupEntityQueries.getAdditionGroupByUuid(uuid).executeAsOneOrNull()
    }
}