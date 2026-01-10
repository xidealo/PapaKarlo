package com.bunbeauty.address.presentation.user_address_list

import com.bunbeauty.core.domain.address.GetSelectableUserAddressListUseCase
import com.bunbeauty.core.domain.address.SaveSelectedUserAddressUseCase
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.base.SharedStateViewModel
import kotlinx.coroutines.launch

class UserAddressListViewModel(
    private val getSelectableUserAddressListUseCase: GetSelectableUserAddressListUseCase,
    private val saveSelectedUserAddressUseCase: SaveSelectedUserAddressUseCase,
) : SharedStateViewModel<UserAddressListDataState.DataState, UserAddressListDataState.Action, UserAddressListDataState.Event>(
        initDataState =
            UserAddressListDataState.DataState(
                userAddressList = emptyList(),
                state = UserAddressListDataState.DataState.State.LOADING,
            ),
    ) {
    override fun reduce(
        action: UserAddressListDataState.Action,
        dataState: UserAddressListDataState.DataState,
    ) {
        when (action) {
            UserAddressListDataState.Action.BackClicked -> onBackClicked()
            UserAddressListDataState.Action.OnClickedCreateAddress -> onCreateAddressClicked()
            UserAddressListDataState.Action.Init -> update()
            UserAddressListDataState.Action.OnRefreshClicked -> update()
        }
    }

    private fun update() {
        setState {
            copy(
                state = UserAddressListDataState.DataState.State.LOADING,
            )
        }
        sharedScope.launchSafe(
            block = {
                val addressList = getSelectableUserAddressListUseCase()
                setState {
                    copy(
                        userAddressList = addressList,
                        state =
                            if (addressList.isEmpty()) {
                                UserAddressListDataState.DataState.State.EMPTY
                            } else {
                                UserAddressListDataState.DataState.State.SUCCESS
                            },
                    )
                }
            },
            onError = {
                setState {
                    copy(
                        state = UserAddressListDataState.DataState.State.ERROR,
                    )
                }
            },
        )
    }

    private fun onCreateAddressClicked() {
        addEvent {
            UserAddressListDataState.Event.OpenCreateAddressEvent
        }
    }

    private fun onBackClicked() {
        addEvent {
            UserAddressListDataState.Event.GoBackEvent
        }
    }

    // using for ios
    fun onUserAddressChanged(userAddressUuid: String) {
        sharedScope.launch {
            saveSelectedUserAddressUseCase(userAddressUuid)
            addEvent {
                UserAddressListDataState.Event.GoBackEvent
            }
            setState {
                copy(
                    state = UserAddressListDataState.DataState.State.LOADING,
                )
            }
            println("EVENTS ${events.value}")
        }
    }
}
