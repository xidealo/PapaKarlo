package com.bunbeauty.shared.data.dao.addition_group

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.bunbeauty.shared.db.AdditionGroupEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase

class AdditionGroupDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : IAdditionGroupDao {
    private val additionGroupEntityQueries = foodDeliveryDatabase.additionGroupEntityQueries

    override suspend fun insertList(additionGroupEntities: List<AdditionGroupEntity>) {
        additionGroupEntities.forEach { additionGroupEntity ->
            additionGroupEntityQueries.insert(additionGroupEntity)
        }
    }

    override suspend fun getAdditionGroupEntity(uuid: String): AdditionGroupEntity? =
        additionGroupEntityQueries.getAdditionGroupByUuid(uuid).awaitAsOneOrNull()

    override suspend fun getAdditionGroupEntityList(menuProductUuid: String): List<AdditionGroupEntity> =
        additionGroupEntityQueries.getAdditionGroupListByMenuProductUuid(menuProductUuid).awaitAsList()
}
