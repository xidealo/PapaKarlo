package com.bunbeauty.papakarlo.presentation.login

import com.bunbeauty.common.Constants
import com.bunbeauty.domain.util.field_helper.IFieldHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.LoginFragmentDirections
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val fieldHelper: IFieldHelper,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {

    fun goToConfirm(phone: String, email: String) {

        if (!fieldHelper.isCorrectFieldContent(
                phone,
                true,
                18,
                18
            )
        ) {
            sendFieldError(
                Constants.PHONE_ERROR_KEY,
                resourcesProvider.getString(com.bunbeauty.papakarlo.R.string.error_create_order_phone)
            )
            return
        }

        router.navigate(LoginFragmentDirections.toConfirmFragment(phone, email))
    }

}