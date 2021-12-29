package com.bunbeauty.domain.repo

import com.bunbeauty.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepo {

    suspend fun refreshCategoryList()
    fun observeCategoryList(): Flow<List<Category>>
}