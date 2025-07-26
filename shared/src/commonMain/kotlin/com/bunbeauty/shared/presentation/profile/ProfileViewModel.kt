package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.core.Logger
import com.bunbeauty.shared.domain.feature.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.feature.order.GetLastOrderUseCase
import com.bunbeauty.shared.domain.feature.order.ObserveLastOrderUseCase
import com.bunbeauty.shared.domain.feature.order.StopObserveOrdersUseCase
import com.bunbeauty.shared.domain.feature.payment.GetPaymentMethodListUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInteractor: IUserInteractor,
    private val getLastOrderUseCase: GetLastOrderUseCase,
    private val getPaymentMethodListUseCase: GetPaymentMethodListUseCase,
    private val getLinkListUseCase: GetLinkListUseCase,
    private val observeLastOrderUseCase: ObserveLastOrderUseCase,
    private val stopObserveOrdersUseCase: StopObserveOrdersUseCase,
) : SharedStateViewModel<ProfileState.DataState, ProfileState.Action, ProfileState.Event>(
    initDataState = ProfileState.DataState(
        lastOrder = null,
        state = ProfileState.DataState.State.LOADING,
        paymentMethodList = persistentListOf(),
        linkList = listOf(),
        isShowAboutAppBottomSheet = false,
        isShowFeedbackBottomSheet = false
    )
) {

    private var observeLastOrderJob: Job? = null
    private var orderObservationUuid: String? = null

    override fun reduce(
        action: ProfileState.Action,
        dataState: ProfileState.DataState,
    ) {
        when (action) {
            ProfileState.Action.BackClicked -> onBackClicked()
            ProfileState.Action.Init -> loadData()
            ProfileState.Action.OnRefreshClicked -> loadData()
            is ProfileState.Action.OnLastOrderClicked -> onLastOrderClicked(
                uuid = action.uuid,
            )

            ProfileState.Action.OnOrderHistoryClicked -> onOrderHistoryClicked()
            ProfileState.Action.OnSettingsClick -> onSettingsClicked()
            ProfileState.Action.OnYourAddressesClicked -> onYourAddressesClicked()
            ProfileState.Action.OnLoginClicked -> onLoginClicked()
            ProfileState.Action.OnAboutAppClicked -> onAboutAppClicked()
            ProfileState.Action.OnCafeListClicked -> onCafeListClicked()
            ProfileState.Action.CloseAboutAppBottomSheet -> onCloseAboutAppBottomSheet()
            ProfileState.Action.OnFeedbackClicked -> onFeedbackClicked()

            is ProfileState.Action.OnPaymentClicked -> onPaymentClicked(
                paymentMethodList = action.paymentMethodList
            )

            ProfileState.Action.StartObserveOrder -> observeLastOrder()

            ProfileState.Action.StopObserveOrder -> stopLastOrderObservation()
            ProfileState.Action.CloseFeedbackBottomSheet -> onCloseFeedbackBottomSheet()
        }
    }

    private fun loadData() {
        sharedScope.launchSafe(
            block = {
                val lastOrder = getLastOrderUseCase()
                val linkList = getLinkListUseCase()
                val paymentMethodList = getPaymentMethodListUseCase()
                setState {
                    copy(
                        lastOrder = lastOrder,
                        state = if (userInteractor.isUserAuthorize()) {
                            ProfileState.DataState.State.AUTHORIZED
                        } else {
                            ProfileState.DataState.State.UNAUTHORIZED
                        },
                        paymentMethodList = paymentMethodList.toImmutableList(),
                        linkList = linkList,
                    )
                }
            },
            onError = {
                setState {
                    copy(
                        state = ProfileState.DataState.State.ERROR
                    )
                }
            }
        )
    }

    private fun observeLastOrder() {
        observeLastOrderJob = sharedScope.launchSafe(
            block = {
                val (uuid, lastOrderFlow) = observeLastOrderUseCase()
                orderObservationUuid = uuid
                lastOrderFlow.collectLatest { lightOrder ->
                    setState {
                        copy(lastOrder = lightOrder)
                    }
                }
            },
            onError = { error ->
                Logger.logE("Profile", error.stackTraceToString())
            }
        )
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

    private fun onBackClicked() {
        addEvent {
            ProfileState.Event.GoBackEvent
        }
    }

    fun onLastOrderClicked(uuid: String) {
        addEvent {
            ProfileState.Event.OpenOrderDetails(uuid)
        }
    }


    fun onSettingsClicked() {
        addEvent {
            ProfileState.Event.OpenSettings
        }
    }

    fun onYourAddressesClicked() {
        addEvent {
            ProfileState.Event.OpenAddressList
        }
    }

    fun onOrderHistoryClicked() {
        addEvent {
            ProfileState.Event.OpenOrderList
        }
    }

    fun onPaymentClicked(paymentMethodList: List<PaymentMethod>) {
        addEvent {
            ProfileState.Event.ShowPayment(
                paymentMethodList = paymentMethodList
            )
        }
    }

    fun onCafeListClicked() {
        addEvent {
            ProfileState.Event.ShowCafeList
        }
    }

    fun onCloseAboutAppBottomSheet() {
        setState {
            copy(
                isShowAboutAppBottomSheet = false
            )
        }
    }

    fun onCloseFeedbackBottomSheet() {
        setState {
            copy(
                isShowFeedbackBottomSheet = false
            )
        }
    }

    fun onFeedbackClicked() {
        setState {
            copy(
                isShowFeedbackBottomSheet = true
            )
        }
    }

    fun onAboutAppClicked() {
        setState {
            copy(
                isShowAboutAppBottomSheet = true
            )
        }
    }

    fun onLoginClicked() {
        addEvent {
            ProfileState.Event.OpenLogin
        }
    }
}
