package com.bunbeauty.shared.data.dao.addition

import com.bunbeauty.shared.db.AdditionEntity

interface IAdditionDao {
    suspend fun insertList(additionEntityList: List<AdditionEntity>)
    suspend fun insert(additionEntity: AdditionEntity)
    suspend fun getAdditionEntity(uuid: String): AdditionEntity?
    suspend fun getAdditionEntityListByAdditionGroup(uuid: String): List<AdditionEntity>
}
