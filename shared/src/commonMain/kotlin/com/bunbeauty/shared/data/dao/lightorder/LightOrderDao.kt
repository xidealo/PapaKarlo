package com.bunbeauty.shared.data.dao.lightorder

import com.bunbeauty.shared.db.LightOrderEntity

interface LightOrderDao {
    suspend fun insertLightOrder(lightOrderEntity: LightOrderEntity)

    suspend fun getLightOrderList(count: Int): List<LightOrderEntity>

    suspend fun updateLightOrderStatusByUuid(
        uuid: String,
        status: String,
    )

    suspend fun deleteAll()
}
