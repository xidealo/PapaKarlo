package com.bunbeauty.papakarlo.view_model

import com.bunbeauty.papakarlo.data.api.firebase.ApiRepository
import com.bunbeauty.papakarlo.ui.menu.MenuNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class MenuViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    BaseViewModel() {

    var navigator: WeakReference<MenuNavigator>? = null

    fun getMenuProductList() {
        apiRepository.getMenuProductList()
    }
}