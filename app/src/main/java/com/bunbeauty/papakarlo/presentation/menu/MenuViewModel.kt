package com.bunbeauty.papakarlo.presentation.menu

import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.base.TopbarCartViewModel
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    cartProductRepo: CartProductRepo,
    stringUtil: IStringHelper,
    productHelper: IProductHelper,
) : TopbarCartViewModel(cartProductRepo, stringUtil, productHelper) {

}