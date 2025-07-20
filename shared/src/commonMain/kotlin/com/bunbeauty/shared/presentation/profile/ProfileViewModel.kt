package com.bunbeauty.shared.presentation.profile

import com.bunbeauty.shared.domain.feature.link.GetLinkListUseCase
import com.bunbeauty.shared.domain.feature.order.GetLastOrderUseCase
import com.bunbeauty.shared.domain.feature.payment.GetPaymentMethodListUseCase
import com.bunbeauty.shared.domain.interactor.user.IUserInteractor
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.payment_method.PaymentMethod
import com.bunbeauty.shared.extension.launchSafe
import com.bunbeauty.shared.presentation.base.SharedStateViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList


class ProfileViewModel(
    private val userInteractor: IUserInteractor,
    private val getLastOrderUseCase: GetLastOrderUseCase,
    private val getPaymentMethodListUseCase: GetPaymentMethodListUseCase,
    private val getLinkListUseCase: GetLinkListUseCase
) : SharedStateViewModel<ProfileState.DataState, ProfileState.Action, ProfileState.Event>(
    initDataState = ProfileState.DataState(
        lastOrder = null,
        state = ProfileState.DataState.State.LOADING,
        paymentMethodList = persistentListOf(),
        linkList = listOf()
    )
) {

    override fun reduce(
        action: ProfileState.Action,
        dataState: ProfileState.DataState
    ) {
        when (action) {
            ProfileState.Action.BackClicked -> onBackClicked()
            ProfileState.Action.Init -> loadData()
            ProfileState.Action.OnRefreshClicked -> loadData()
            is ProfileState.Action.onLastOrderClicked -> onLastOrderClicked(
                uuid = action.uuid,
            )

            ProfileState.Action.onOrderHistoryClicked -> onOrderHistoryClicked()
            ProfileState.Action.onSettingsClick -> onSettingsClicked()
            ProfileState.Action.onYourAddressesClicked -> onYourAddressesClicked()
            ProfileState.Action.onLoginClicked -> onLoginClicked()
            ProfileState.Action.onAboutAppClicked -> onAboutAppClicked()
            ProfileState.Action.onCafeListClicked -> onCafeListClicked()
            is ProfileState.Action.onFeedbackClicked -> onFeedbackClicked(
                action.linkList
            )

            is ProfileState.Action.onPaymentClicked -> onPaymentClicked(
                paymentMethodList = action.paymentMethodList
            )
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

    fun onFeedbackClicked(linkList: List<Link>) {
        addEvent {
            ProfileState.Event.ShowFeedback(
                linkList = linkList
            )
        }
    }

    fun onAboutAppClicked() {
        addEvent {
            ProfileState.Event.ShowAboutApp
        }
    }

    fun onLoginClicked() {
        addEvent {
            ProfileState.Event.OpenLogin
        }
    }
}
