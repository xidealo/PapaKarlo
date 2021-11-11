package com.bunbeauty.papakarlo.presentation.profile.settings

import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.LogoutBottomSheetDirections.backToProfileFragment
import javax.inject.Inject

class LogoutViewModel @Inject constructor(
    private val userInteractor: IUserInteractor
) : BaseViewModel() {

    fun logout() {
        userInteractor.logout()
        router.navigate(backToProfileFragment())
    }
}