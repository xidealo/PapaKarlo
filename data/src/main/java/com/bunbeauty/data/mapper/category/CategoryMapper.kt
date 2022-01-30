package com.bunbeauty.data.mapper.category

import com.bunbeauty.data.database.entity.CategoryEntity
import com.bunbeauty.data.network.model.CategoryServer
import com.bunbeauty.domain.model.category.Category
import javax.inject.Inject

class CategoryMapper @Inject constructor() : ICategoryMapper {

    override fun toModel(category: CategoryEntity): Category {
        return Category(
            uuid = category.uuid,
            name = category.name,
            priority = category.priority,
        )
    }

    override fun toEntityModel(category: CategoryServer): CategoryEntity {
        return CategoryEntity(
            uuid = category.uuid,
            name = category.name,
            priority = category.priority,
        )
    }
}