package com.bunbeauty.papakarlo.presentation.login

import com.bunbeauty.domain.util.field_helper.IFieldHelper
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.LoginFragmentDirections
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val fieldHelper: IFieldHelper
) : BaseViewModel() {

    fun goToConfirm(phone: String, email: String) {
        router.navigate(LoginFragmentDirections.toConfirmFragment(phone, email))
    }

}