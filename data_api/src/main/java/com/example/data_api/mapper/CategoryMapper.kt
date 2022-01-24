package com.example.data_api.mapper

import com.bunbeauty.domain.model.category.Category
import com.example.domain_api.mapper.ICategoryMapper
import com.example.domain_api.model.entity.CategoryEntity
import com.example.domain_api.model.server.CategoryServer
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