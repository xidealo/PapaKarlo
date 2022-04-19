package com.bunbeauty.papakarlo.feature.splash

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.interactor.update.IUpdateInteractor
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.feature.splash.SplashFragmentDirections.*
import kotlinx.coroutines.launch

class SplashViewModel(
    private val updateInteractor: IUpdateInteractor,
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    init {
        checkIsUpdated()
    }

    private fun checkIsUpdated() {
        viewModelScope.launch {
            val isUpdated = updateInteractor.checkIsUpdated(BuildConfig.VERSION_CODE)
            if (isUpdated) {
                checkIsCitySelected()
            } else {
                router.navigate(toUpdateFragment())
            }
        }
    }

    private suspend fun checkIsCitySelected() {
        val isCitySelected = cityInteractor.checkIsCitySelected()
        if (isCitySelected) {
            router.navigate(toMenuFragment())
        } else {
            router.navigate(toSelectCityFragment())
        }
    }
}