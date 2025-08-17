package com.bunbeauty.papakarlo.feature.main

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bunbeauty.core.Logger
import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import com.bunbeauty.shared.domain.feature.orderavailable.IsOrderAvailableUseCase
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
    private val isOrderAvailableUseCase: IsOrderAvailableUseCase
) : ViewModel() {

    private val mutableMainState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = mutableMainState.asStateFlow()

    init {
        observeNetworkConnection()
        checkStatusBarMessage()
    }

    fun showInfoMessage(text: String, paddingBottom: Int) {
        showMessage(
            text = text,
            type = FoodDeliveryMessageType.INFO,
            paddingBottom = paddingBottom
        )
    }

    fun showErrorMessage(text: String) {
        showMessage(
            text = text,
            type = FoodDeliveryMessageType.ERROR,
            paddingBottom = 0
        )
    }

    fun consumeEventList(eventList: List<MainState.Event>) {
        mutableMainState.update { state ->
            state - eventList
        }
    }

    fun setStatusColor(color: Color) {
        mutableMainState.update { state ->
            state.copy(statusBarColor = color)
        }
    }

    private fun showMessage(text: String, type: FoodDeliveryMessageType, paddingBottom: Int) {
        mutableMainState.update { state ->
            state.copy(
                paddingBottomSnackbar = paddingBottom
            ) + MainState.Event.ShowMessageEvent(
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

    private fun checkStatusBarMessage() {
        viewModelScope.launchSafe(
            block = {
                mutableMainState.update { state ->
                    state.copy(
                        statusBarMessage = MainState.StatusBarMessage(
                            isVisible = !isOrderAvailableUseCase()
                        )
                    )
                }
            },
            onError = { error ->
                Logger.logE(MAIN_VIEW_MODEL_TAG, error.stackTraceToString())
            }
        )
    }
}
