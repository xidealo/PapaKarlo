package com.bunbeauty.papakarlo.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bunbeauty.core.Logger
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.bottombar.NavigationBarItem
import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import com.bunbeauty.shared.domain.feature.orderavailable.IsOrderAvailableUseCase
import com.bunbeauty.shared.domain.feature.orderavailable.SetOrderNotAvailableUseCase
import com.bunbeauty.shared.extension.launchSafe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

private const val MAIN_VIEW_MODEL_TAG = "MainViewModel"

class MainViewModel(
    private val networkUtil: INetworkUtil,
    private val isOrderAvailableUseCase: IsOrderAvailableUseCase,
    private val setOrderNotAvailableUseCase: SetOrderNotAvailableUseCase,
) : ViewModel() {

    private val mutableMainState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = mutableMainState.asStateFlow()

    init {
        observeNetworkConnection()
        checkIsOrderAvailable()
    }

    fun onNavDestinationUpdated(destinationId: Int, navController: NavController) {
        val navigationBarOptions = when (destinationId) {
            R.id.cafeListFragment -> NavigationBarOptions.Visible(
                selectedItem = NavigationBarItem.CAFE_LIST,
                navController = navController
            )

            R.id.menuFragment -> NavigationBarOptions.Visible(
                selectedItem = NavigationBarItem.MENU,
                navController = navController
            )

            R.id.profileFragment -> NavigationBarOptions.Visible(
                selectedItem = NavigationBarItem.PROFILE,
                navController = navController
            )

            else -> NavigationBarOptions.Hidden
        }
        mutableMainState.update { state ->
            state.copy(navigationBarOptions = navigationBarOptions)
        }
    }

    fun showInfoMessage(text: String) {
        showMessage(
            text = text,
            type = FoodDeliveryMessageType.INFO
        )
    }

    fun showErrorMessage(text: String) {
        showMessage(
            text = text,
            type = FoodDeliveryMessageType.ERROR
        )
    }

    fun consumeEventList(eventList: List<MainState.Event>) {
        mutableMainState.update { state ->
            state - eventList
        }
    }

    private fun showMessage(text: String, type: FoodDeliveryMessageType) {
        mutableMainState.update { state ->
            state + MainState.Event.ShowMessageEvent(
                message = FoodDeliveryMessage(
                    type = type,
                    text = text
                )
            )
        }
    }

    private fun observeNetworkConnection() {
        networkUtil.observeIsOnline().onEach { isOnline ->
            mutableMainState.update { state ->
                state.copy(connectionLost = !isOnline)
            }
        }.launchIn(viewModelScope)
    }

    private fun checkIsOrderAvailable() {
        viewModelScope.launchSafe(
            block = {
                mutableMainState.update { state ->
                    state.copy(showOrderNotAvailable = isOrderAvailableUseCase().not())
                }
            },
            onError = { error ->
                Logger.logE(MAIN_VIEW_MODEL_TAG, error.stackTraceToString())
            }
        )
    }

    fun setOrderNotAvailable() {
        setOrderNotAvailableUseCase()
        mutableMainState.update { state ->
            state.copy(showOrderNotAvailable = true)
        }
    }
}
