package com.bunbeauty.shared.data.dao.addition

import com.bunbeauty.shared.db.AdditionEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase

class AdditionDao(foodDeliveryDatabase: FoodDeliveryDatabase) : IAdditionDao {

    private val additionEntityQueries = foodDeliveryDatabase.additionEntityQueries

    override fun insertList(additionEntityList: List<AdditionEntity>) {
        additionEntityList.forEach { additionEntity ->
            insert(additionEntity)
        }
    }

    override fun insert(additionEntity: AdditionEntity) {
        additionEntityQueries.insert(
            uuid = additionEntity.uuid,
            name = additionEntity.name,
            price = additionEntity.price,
            isVisible = additionEntity.isVisible,
            isSelected = additionEntity.isSelected,
            menuProductUuid = additionEntity.menuProductUuid,
            photoLink = additionEntity.photoLink
        )
    }

    override fun getAdditionEntity(uuid: String): AdditionEntity? {
        return additionEntityQueries.getAdditionByUuid(uuid).executeAsOneOrNull()

    }
}