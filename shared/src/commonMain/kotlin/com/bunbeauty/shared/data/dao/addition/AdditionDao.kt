package com.bunbeauty.shared.data.dao.addition

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.bunbeauty.shared.db.AdditionEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase

class AdditionDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : IAdditionDao {
    private val additionEntityQueries = foodDeliveryDatabase.additionEntityQueries

    override suspend fun insertList(additionEntityList: List<AdditionEntity>) {
        additionEntityList.forEach { additionEntity ->
            insert(additionEntity)
        }
    }

    override suspend fun insert(additionEntity: AdditionEntity) {
        additionEntityQueries.insert(additionEntity)
    }

    override suspend fun getAdditionEntity(uuid: String): AdditionEntity? = additionEntityQueries.getAdditionByUuid(uuid).awaitAsOneOrNull()

    override suspend fun getAdditionEntityListByAdditionGroup(uuid: String): List<AdditionEntity> =
        additionEntityQueries.getAdditionByAdditionGroupUuid(uuid).awaitAsList()
}
