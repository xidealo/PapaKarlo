package com.bunbeauty.domain.interactor.categories

import com.bunbeauty.domain.model.MenuModel
import com.bunbeauty.domain.model.category.Category
import com.bunbeauty.domain.repo.CategoryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val categoryRepo: CategoryRepo
) : ICategoryInteractor {

    override fun observeCategoryList(): Flow<List<Category>> {
        return categoryRepo.observeCategoryList().map { categoryList ->
            categoryList.sortedBy { category ->
                category.priority
            }
        }
    }

    override fun getCurrentCategory(
        menuPosition: Int,
        menuList: List<MenuModel>
    ): MenuModel.Section? {
        return menuList.filterIsInstance<MenuModel.Section>()
            .findLast { section ->
                val position = menuList.indexOf(section)
                menuPosition >= position
            }
    }

}