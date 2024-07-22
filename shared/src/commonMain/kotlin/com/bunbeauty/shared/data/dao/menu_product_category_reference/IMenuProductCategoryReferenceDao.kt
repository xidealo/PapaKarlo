package com.bunbeauty.shared.data.dao.menu_product_category_reference

import com.bunbeauty.shared.db.MenuProductCategoryReference

interface IMenuProductCategoryReferenceDao {

    suspend fun updateMenuProductReferenceList(menuProductCategoryReferenceList: List<MenuProductCategoryReference>)
}
