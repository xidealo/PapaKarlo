package com.bunbeauty.papakarlo.presentation

import com.bunbeauty.domain.field_helper.IFieldHelper
import com.bunbeauty.papakarlo.presentation.base.ToolbarViewModel
import com.bunbeauty.papakarlo.ui.LoginFragmentDirections
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val iFieldHelper: IFieldHelper
) : ToolbarViewModel() {

    fun goToConfirm(phone: String, email: String) {
        router.navigate(LoginFragmentDirections.toConfirmFragment(phone, email))
    }

}