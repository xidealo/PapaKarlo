package com.example.data_api.repository

import com.bunbeauty.common.Logger.CATEGORY_TAG
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.category.Category
import com.bunbeauty.domain.repo.CategoryRepo
import com.example.data_api.dao.CategoryDao
import com.example.data_api.handleListResult
import com.example.domain_api.mapper.ICategoryMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
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