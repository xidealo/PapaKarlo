package com.bunbeauty.profile.presentation.profile

import com.bunbeauty.core.Constants.VERSION_DIVIDER
import com.bunbeauty.core.Logger
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.link.GetLinkListUseCase
import com.bunbeauty.core.domain.order.GetLastOrderUseCase
import com.bunbeauty.core.domain.order.ObserveLastOrderUseCase
import com.bunbeauty.core.domain.order.StopObserveOrdersUseCase
import com.bunbeauty.core.domain.user.IUserInteractor
import com.bunbeauty.core.extension.launchSafe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userInteractor: IUserInteractor,
    private val getLinkListUseCase: GetLinkListUseCase,
    buildVersion: Long,
) : SharedStateViewModel<ProfileState.DataState, ProfileState.Action, ProfileState.Event>(
    initDataState =
        ProfileState.DataState(
            state = ProfileState.DataState.State.LOADING,
            linkList = listOf(),
            isShowAboutAppBottomSheet = false,
            isShowFeedbackBottomSheet = false,
            appVersion = buildVersion.toString().toCharArray().joinToString(VERSION_DIVIDER),
        ),
) {


    init {
        loadData()
    }

    override fun reduce(
        action: ProfileState.Action,
        dataState: ProfileState.DataState,
    ) {
        when (action) {
            ProfileState.Action.BackClicked -> onBackClicked()
            ProfileState.Action.OnRefreshClicked -> loadData()

            ProfileState.Action.OnOrderHistoryClicked -> onOrderHistoryClicked()
            ProfileState.Action.OnSettingsClick -> onSettingsClicked()
            ProfileState.Action.OnYourAddressesClicked -> onYourAddressesClicked()
            ProfileState.Action.OnLoginClicked -> onLoginClicked()
            ProfileState.Action.OnAboutAppClicked -> onAboutAppClicked()
            ProfileState.Action.OnCafeListClicked -> onCafeListClicked()
            ProfileState.Action.CloseAboutAppBottomSheet -> onCloseAboutAppBottomSheet()
            ProfileState.Action.OnFeedbackClicked -> onFeedbackClicked()
            ProfileState.Action.CloseFeedbackBottomSheet -> onCloseFeedbackBottomSheet()
        }
    }

    private fun loadData() {
        sharedScope.launchSafe(
            block = {
                val linkList = getLinkListUseCase()
                setState {
                    copy(
                        state =
                            if (userInteractor.isUserAuthorize()) {
                                ProfileState.DataState.State.AUTHORIZED
                            } else {
                                ProfileState.DataState.State.UNAUTHORIZED
                            },
                        linkList = linkList,
                    )
                }
            },
            onError = {
                setState {
                    copy(
                        state = ProfileState.DataState.State.ERROR,
                    )
                }
            },
        )
    }

    private fun onBackClicked() {
        addEvent {
            ProfileState.Event.GoBackEvent
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

    fun onCafeListClicked() {
        addEvent {
            ProfileState.Event.ShowCafeList
        }
    }

    fun onCloseAboutAppBottomSheet() {
        setState {
            copy(
                isShowAboutAppBottomSheet = false,
            )
        }
    }

    fun onCloseFeedbackBottomSheet() {
        setState {
            copy(
                isShowFeedbackBottomSheet = false,
            )
        }
    }

    fun onFeedbackClicked() {
        setState {
            copy(
                isShowFeedbackBottomSheet = true,
            )
        }
    }

    fun onAboutAppClicked() {
        setState {
            copy(
                isShowAboutAppBottomSheet = true,
            )
        }
    }

    fun onLoginClicked() {
        addEvent {
            ProfileState.Event.OpenLogin
        }
    }
}
