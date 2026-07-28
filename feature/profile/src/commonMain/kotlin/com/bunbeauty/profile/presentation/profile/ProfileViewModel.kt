package com.bunbeauty.profile.presentation.profile

import com.bunbeauty.core.Constants.VERSION_DIVIDER
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.auth.ObserveTokenUseCase
import com.bunbeauty.core.domain.link.GetLinkListUseCase
import com.bunbeauty.core.extension.launchSafe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class ProfileViewModel(
    private val observeTokenUseCase: ObserveTokenUseCase,
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
    private var observeTokenJob: Job? = null

    init {
        observeToken()
        loadLinks()
    }

    override fun reduce(
        action: ProfileState.Action,
        dataState: ProfileState.DataState,
    ) {
        when (action) {
            ProfileState.Action.BackClicked -> onBackClicked()
            ProfileState.Action.OnRefreshClicked -> {
                observeToken()
                loadLinks()
            }

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

    private fun loadLinks() {
        sharedScope.launchSafe(
            block = {
                val linkList = getLinkListUseCase()
                setState {
                    copy(linkList = linkList)
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

    private fun observeToken() {
        observeTokenJob?.cancel()
        observeTokenJob =
            sharedScope.launchSafe(
                block = {
                    observeTokenUseCase().collectLatest { token ->
                        setState {
                            copy(
                                state =
                                    if (token != null) {
                                        ProfileState.DataState.State.AUTHORIZED
                                    } else {
                                        ProfileState.DataState.State.UNAUTHORIZED
                                    },
                            )
                        }
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
