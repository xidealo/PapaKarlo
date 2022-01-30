package com.bunbeauty.papakarlo.feature.profile.settings.logout

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.user.IUserInteractor
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.profile.settings.logout.LogoutBottomSheetDirections.backToProfileFragment
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