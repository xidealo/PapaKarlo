package com.bunbeauty.data.mapper.category

import com.bunbeauty.data.network.model.CategoryServer
import com.bunbeauty.domain.model.category.Category
import database.CategoryEntity

interface ICategoryMapper {

    fun toModel(category: CategoryEntity): Category
    fun toEntityModel(category: CategoryServer): CategoryEntity
}