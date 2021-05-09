package com.bunbeauty.papakarlo.presentation.login

import com.bunbeauty.common.Constants.PHONE_LENGTH
import com.bunbeauty.domain.util.validator.ITextValidator
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.LoginFragmentDirections.toConfirmFragment
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val textValidator: ITextValidator,
    private val resourcesProvider: IResourcesProvider
) : BaseViewModel() {

    fun onNextClicked(phone: String) {
        if (textValidator.isFieldContentCorrect(phone, PHONE_LENGTH, PHONE_LENGTH)) {
            router.navigate(toConfirmFragment(phone))
        } else {
            showError(resourcesProvider.getString(com.bunbeauty.papakarlo.R.string.error_login_phone))
        }
    }
}