package com.bunbeauty.papakarlo.presentation.menu

import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper,
    val resourcesProvider: IResourcesProvider
) : CartViewModel(cartProductRepo, stringUtil, productHelper) {

}