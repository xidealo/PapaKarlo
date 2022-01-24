package com.bunbeauty.papakarlo.presentation.profile.settings

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.LogoutBottomSheetDirections.backToProfileFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogoutViewModel @Inject constructor(
    private val userInteractor: IUserInteractor
) : BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            userInteractor.logout()
            router.navigate(backToProfileFragment())
        }
    }
}