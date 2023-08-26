package com.bunbeauty.papakarlo.feature.splash

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.common.viewmodel.BaseViewModel
import com.bunbeauty.shared.domain.interactor.city.ICityInteractor
import com.bunbeauty.shared.domain.interactor.update.IUpdateInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val updateInteractor: IUpdateInteractor,
    private val cityInteractor: ICityInteractor
) : BaseViewModel() {

    private val mutableEventList = MutableStateFlow<List<SplashEvent>>(emptyList())
    val eventList = mutableEventList.asStateFlow()

    fun checkAppVersion() {
        viewModelScope.launch {
            val isUpdated = updateInteractor.checkIsUpdated(BuildConfig.VERSION_CODE)
            if (isUpdated) {
                checkIsCitySelected()
            } else {
                mutableEventList.update { list ->
                    list + SplashEvent.NavigateToUpdateEvent
                }
            }
        }
    }

    fun consumeEventList(eventList: List<SplashEvent>) {
        mutableEventList.update { list ->
            list - eventList.toSet()
        }
    }

    private suspend fun checkIsCitySelected() {
        val isCitySelected = cityInteractor.checkIsCitySelected()
        val navigateEvent = if (isCitySelected) {
            SplashEvent.NavigateToMenuEvent
        } else {
            SplashEvent.NavigateToSelectCityEvent
        }
        mutableEventList.update { list ->
            list + navigateEvent
        }
    }
}
