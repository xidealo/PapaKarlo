package com.bunbeauty.shared.data.dao.addition

import com.bunbeauty.shared.db.AdditionEntity

interface IAdditionDao {
    fun insertList(additionEntityList: List<AdditionEntity>)
    fun insert(additionEntity: AdditionEntity)
    fun getAdditionEntity(uuid: String): AdditionEntity?
}