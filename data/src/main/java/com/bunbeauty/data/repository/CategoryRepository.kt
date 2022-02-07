package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.CATEGORY_TAG
import com.bunbeauty.data.database.dao.CategoryDao
import com.bunbeauty.data.handleListResult
import com.bunbeauty.data.mapper.category.ICategoryMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.category.Category
import com.bunbeauty.domain.repo.CategoryRepo
import kotlinx.coroutines.flow.Flow

class CategoryRepository  constructor(
    private val apiRepository: ApiRepo,
    private val categoryMapper: ICategoryMapper,
    private val categoryDao: CategoryDao
) : CategoryRepo {

    override suspend fun refreshCategoryList() {
        apiRepository.getCategoryList().handleListResult(CATEGORY_TAG) { categoryList ->
            if (categoryList != null) {
                val categoryEntityList = categoryList.map(categoryMapper::toEntityModel)
                categoryDao.insertAll(categoryEntityList)
            }
        }
    }

    override fun observeCategoryList(): Flow<List<Category>> {
        return categoryDao.observeCategoryList().mapListFlow(categoryMapper::toModel)
    }
}