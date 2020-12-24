package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.ui.product.ProductNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ProductViewModel @Inject constructor() : BaseViewModel() {

    var navigator: WeakReference<ProductNavigator>? = null

}