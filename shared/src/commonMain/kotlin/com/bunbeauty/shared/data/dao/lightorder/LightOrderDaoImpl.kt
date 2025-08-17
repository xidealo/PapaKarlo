package com.bunbeauty.shared.data.dao.lightorder

import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.bunbeauty.shared.db.LightOrderEntity

class LightOrderDaoImpl(foodDeliveryDatabase: FoodDeliveryDatabase) : LightOrderDao {

    private val lightOrderEntityQueries = foodDeliveryDatabase.lightOrderEntityQueries

    override suspend fun insertLightOrder(lightOrderEntity: LightOrderEntity) {
        lightOrderEntityQueries.isnsertOrder(lightOrderEntity)
    }

    override suspend fun getLightOrderList(
        count: Int
    ): List<LightOrderEntity> {
        return lightOrderEntityQueries.getLightOrderList(count.toLong()).executeAsList()
    }

    override suspend fun updateLightOrderStatusByUuid(uuid: String, status: String) {
        lightOrderEntityQueries.updateOrderStatusByUuid(
            uuid = uuid,
            status = status
        )
    }

    override suspend fun deleteAll() {
        lightOrderEntityQueries.deleteAll()
    }
}
