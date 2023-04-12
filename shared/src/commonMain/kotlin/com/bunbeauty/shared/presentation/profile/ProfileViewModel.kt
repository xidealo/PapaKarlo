package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.order.GetLastOrderUseCase
import com.bunbeauty.shared.domain.feature.order.ObserveLastOrderUseCase
import com.bunbeauty.shared.domain.feature.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.order.LightOrder
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInteractor: IUserInteractor,
    private val getLastOrderUseCase: GetLastOrderUseCase,
    private val observeLastOrderUseCase: ObserveLastOrderUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
) : SharedViewModel() {

    private val mutableProfileState = MutableStateFlow(ProfileState())
    val profileState = mutableProfileState.asCommonStateFlow()

    var observeLastOrderJob: Job? = null
    private var orderObservationUuid: String? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableProfileState.update { oldState ->
            oldState.copy(
                state = ProfileState.State.ERROR
            )
        }
    }

    init {
        observeCart()
    }

    fun update() {
        mutableProfileState.update { profileState ->
            profileState.copy(state = ProfileState.State.LOADING)
        }
        sharedScope.launch {
            mutableProfileState.update { profileState ->
                if (userInteractor.isUserAuthorize()) {
                    profileState.copy(
                        state = ProfileState.State.AUTHORIZED,
                        lastOrder = getLastOrderUseCase()
                    )
                } else {
                    profileState.copy(state = ProfileState.State.UNAUTHORIZED)
                }
            }
        }
    }

    fun observeLastOrder() {
        observeLastOrderJob = sharedScope.launch {
            val (uuid, lastOrderFlow) = observeLastOrderUseCase()
            orderObservationUuid = uuid
            lastOrderFlow.collectLatest { lightOrder ->
                mutableProfileState.update { profileState ->
                    profileState.copy(lastOrder = lightOrder)
                }
            }
        }
    }

    private fun observeCart() {
        sharedScope.launch(exceptionHandler) {
            observeCartUseCase().collectLatest { cartTotalAndCount ->
                mutableProfileState.update { state ->
                    state.copy(cartCostAndCount = cartTotalAndCount)
                }
            }
        }
    }

    fun stopLastOrderObservation() {
        observeLastOrderJob?.cancel()
        orderObservationUuid?.let { uuid ->
            sharedScope.launch {
                stopObserveOrdersUseCase(uuid)
            }
        }
        orderObservationUuid = null
    }

    fun onLastOrderClicked(uuid: String, code: String) {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.OpenOrderDetails(uuid, code)
        }
    }

    fun onSettingsClicked() {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.OpenSettings
        }
    }

    fun onYourAddressesClicked() {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.OpenAddressList
        }
    }

    fun onOrderHistoryClicked() {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.OpenOrderList
        }
    }

    fun onPaymentClicked() {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.ShowPayment
        }
    }

    fun onFeedbackClicked() {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.ShowFeedback
        }
    }

    fun onAboutAppClicked() {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.ShowAboutApp
        }
    }

    fun onLoginClicked() {
        mutableProfileState.update { profileState ->
            profileState + ProfileState.Event.OpenLogin
        }
    }

    fun consumeEventList(eventList: List<ProfileState.Event>) {
        mutableProfileState.update { profileState ->
            profileState - eventList
        }
    }
}
