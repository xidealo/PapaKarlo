package com.bunbeauty.domain.interactor.categories

import com.bunbeauty.domain.model.category.Category
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CategoryRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryInteractor @Inject constructor(@Api private val categoryRepo: CategoryRepo) :
    ICategoryInteractor {

    override fun observeCategoryList(): Flow<List<Category>> {
        return categoryRepo.observeCategoryList()
    }
}