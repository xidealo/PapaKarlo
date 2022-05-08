package com.bunbeauty.shared.data.dao.category

import database.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface ICategoryDao {

    suspend fun insertCategoryList(categoryList: List<CategoryEntity>)

    fun observeCategoryList(): Flow<List<CategoryEntity>>

}