package com.example.domain_api.mapper

import com.bunbeauty.domain.model.category.Category
import com.example.domain_api.model.entity.CategoryEntity
import com.example.domain_api.model.server.CategoryServer

interface ICategoryMapper {

    fun toModel(category: CategoryEntity): Category
    fun toEntityModel(category: CategoryServer): CategoryEntity
}