package com.bunbeauty.papakarlo.feature.profile.screen.logout

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.auth.FirebaseAuthRepository
import com.bunbeauty.papakarlo.feature.profile.screen.logout.LogoutBottomSheetDirections.backToProfileFragment
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import kotlinx.coroutines.launch

class LogoutViewModel(
    private val userInteractor: IUserInteractor,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            firebaseAuthRepository.signOut()
            userInteractor.clearUserCache()
            router.navigate(backToProfileFragment())
        }
    }
}
