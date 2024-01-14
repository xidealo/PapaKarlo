package com.bunbeauty.shared.data.dao.addition_group

import com.bunbeauty.shared.db.AdditionGroupEntity

interface IAdditionGroupDao {

    suspend fun insertList(additionGroupEntities: List<AdditionGroupEntity>)

}