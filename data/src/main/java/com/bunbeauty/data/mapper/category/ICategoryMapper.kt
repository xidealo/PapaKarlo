package com.bunbeauty.data.mapper.category

import com.bunbeauty.data.database.entity.CategoryEntity
import com.bunbeauty.data.network.model.CategoryServer
import com.bunbeauty.domain.model.category.Category

interface ICategoryMapper {

    fun toModel(category: CategoryEntity): Category
    fun toEntityModel(category: CategoryServer): CategoryEntity
}