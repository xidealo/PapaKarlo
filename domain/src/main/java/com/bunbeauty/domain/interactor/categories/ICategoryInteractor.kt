package com.bunbeauty.domain.interactor.categories

import com.bunbeauty.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryInteractor {

    fun observeCategoryList(): Flow<List<Category>>
}