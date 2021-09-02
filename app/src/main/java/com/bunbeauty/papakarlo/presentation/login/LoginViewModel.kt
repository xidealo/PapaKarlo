package com.bunbeauty.papakarlo.presentation.login

import com.bunbeauty.common.Constants.PHONE_LENGTH
import com.bunbeauty.domain.util.field_helper.IFieldHelper
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.LoginFragmentDirections.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val fieldHelper: IFieldHelper,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {

    fun goToConfirm(phone: String) {

        if (!fieldHelper.isCorrectFieldContent(phone, true, PHONE_LENGTH, PHONE_LENGTH)) {
            showError(resourcesProvider.getString(com.bunbeauty.papakarlo.R.string.error_login_phone))
            return
        }

        router.navigate(toConfirmFragment(phone))
    }
}