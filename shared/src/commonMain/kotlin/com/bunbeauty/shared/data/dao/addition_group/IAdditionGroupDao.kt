package com.bunbeauty.shared.data.dao.addition_group

import com.bunbeauty.shared.db.AdditionGroupEntity

interface IAdditionGroupDao {
    suspend fun insertList(additionGroupEntities: List<AdditionGroupEntity>)

    suspend fun getAdditionGroupEntity(uuid: String): AdditionGroupEntity?

    suspend fun getAdditionGroupEntityList(menuProduct: String): List<AdditionGroupEntity>
}
