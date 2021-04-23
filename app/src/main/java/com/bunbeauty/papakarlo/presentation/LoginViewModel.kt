package com.bunbeauty.papakarlo.presentation

import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.LoginFragmentDirections
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ToolbarViewModel() {

    fun goToConfirm(phone: String) {
        router.navigate(LoginFragmentDirections.toConfirmFragment(phone))
    }

}