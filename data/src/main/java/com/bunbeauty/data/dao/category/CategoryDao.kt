package com.bunbeauty.data.dao.category

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryDao(foodDeliveryDatabase: FoodDeliveryDatabase) : ICategoryDao {

    private val categoryEntityQueries = foodDeliveryDatabase.categoryEntityQueries

    override suspend fun insertCategoryList(categoryList: List<CategoryEntity>) {
        categoryEntityQueries.transaction {
            categoryList.onEach { categoryEntity ->
                categoryEntityQueries.insertCategory(
                    uuid = categoryEntity.uuid,
                    name = categoryEntity.name,
                    priority = categoryEntity.priority
                )
            }
        }
    }

    override fun observeCategoryList(): Flow<List<CategoryEntity>> {
        return categoryEntityQueries.getCategoryList().asFlow().mapToList()
    }

}