package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.ui.menu.MenuNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class MenuViewModel @Inject constructor() : BaseViewModel() {

    var navigator: WeakReference<MenuNavigator>? = null
    var selectedTabIndex = 0
}