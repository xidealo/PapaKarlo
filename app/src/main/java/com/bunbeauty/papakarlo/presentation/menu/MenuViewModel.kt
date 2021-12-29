package com.bunbeauty.papakarlo.presentation.menu

import com.bunbeauty.domain.interactor.categories.ICategoryInteractor
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.presentation.item.CategoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val categoryInteractor: ICategoryInteractor
) : CartViewModel() {

    private val mutableCategoryList: MutableStateFlow<List<CategoryItem>> =
        MutableStateFlow(emptyList())
    val categoryList: StateFlow<List<CategoryItem>> = mutableCategoryList.asStateFlow()

    init {
        subscribeOnCategoryList()
    }

    private fun subscribeOnCategoryList() {
        categoryInteractor.observeCategoryList().launchOnEach { categoryList ->
            mutableCategoryList.value = categoryList.map { category ->
                CategoryItem(
                    uuid = category.uuid,
                    name = category.name,
                )
            }
        }
    }

}