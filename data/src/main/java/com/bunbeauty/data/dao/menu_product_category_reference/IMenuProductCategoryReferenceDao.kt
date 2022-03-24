package com.bunbeauty.data.dao.menu_product_category_reference

import database.MenuProductCategoryReference

interface IMenuProductCategoryReferenceDao {

    suspend fun updateMenuProductReferenceList(menuProductCategoryReferenceList: List<MenuProductCategoryReference>)

}