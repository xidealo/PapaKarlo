package com.bunbeauty.papakarlo.presentation.profile.settings

import com.bunbeauty.domain.auth.IAuthUtil
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.LogoutBottomSheetDirections.backToProfileFragment
import javax.inject.Inject

class LogoutViewModel @Inject constructor(
    private val authUtil: IAuthUtil
) : BaseViewModel() {

    fun logout() {
        authUtil.signOut()
        router.navigate(backToProfileFragment())
    }
}