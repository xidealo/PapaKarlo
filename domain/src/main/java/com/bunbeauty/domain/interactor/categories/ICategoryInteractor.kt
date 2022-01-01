package com.bunbeauty.domain.interactor.categories

import com.bunbeauty.domain.model.MenuModel
import com.bunbeauty.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryInteractor {

    fun observeCategoryList(): Flow<List<Category>>
    fun getCurrentCategory(menuPosition: Int, menuList: List<MenuModel>): MenuModel.Section?
}