package com.bunbeauty.papakarlo.presentation

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.city.ICityInteractor
import com.bunbeauty.domain.interactor.update.IUpdateInteractor
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.papakarlo.ui.fragment.SplashFragmentDirections.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val updateInteractor: IUpdateInteractor,
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    fun checkIsUpdated() {
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
            router.navigate(toNavMenu())
        } else {
            router.navigate(toSelectCityFragment())
        }
    }
}