package com.bunbeauty.papakarlo.feature.splash

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.interactor.update.IUpdateInteractor
import kotlinx.coroutines.launch

class SplashViewModel(
    private val updateInteractor: IUpdateInteractor,
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    fun checkAppVersion() {
        viewModelScope.launch {
            val isUpdated = updateInteractor.checkIsUpdated(BuildConfig.VERSION_CODE)
            if (isUpdated) {
                checkIsCitySelected()
            } else {
                router.navigate(SplashFragmentDirections.toUpdateFragment())
            }
        }
    }

    private suspend fun checkIsCitySelected() {
        val isCitySelected = cityInteractor.checkIsCitySelected()
        if (isCitySelected) {
            router.navigate(SplashFragmentDirections.toMenuFragment())
        } else {
            router.navigate(SplashFragmentDirections.toSelectCityFragment())
        }
    }
}
