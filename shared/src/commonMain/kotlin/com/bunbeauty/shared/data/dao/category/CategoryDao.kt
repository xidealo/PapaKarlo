package com.bunbeauty.shared.data.dao.category

import com.bunbeauty.shared.db.CategoryEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
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