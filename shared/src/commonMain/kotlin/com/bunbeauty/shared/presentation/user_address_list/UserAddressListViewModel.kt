package com.bunbeauty.shared.presentation.user_address_list

import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.interactor.address.GetUserAddressListUseCase
import com.bunbeauty.shared.domain.interactor.address.IAddressInteractor
import com.bunbeauty.shared.presentation.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserAddressListViewModel(
    private val getUserAddressList: GetUserAddressListUseCase,
    private val addressInteractor: IAddressInteractor
) : SharedViewModel() {

    private val mutableAddressListState = MutableStateFlow(UserAddressListState())
    val addressListState = mutableAddressListState.asCommonStateFlow()

    fun update() {
        mutableAddressListState.update { state ->
            state.copy(state = UserAddressListState.State.LOADING)
        }
        sharedScope.launch {
            val addressList = getUserAddressList()
            mutableAddressListState.update { state ->
                state.copy(
                    userAddressList = addressList,
                    state = if (addressList.isEmpty())
                        UserAddressListState.State.EMPTY
                    else
                        UserAddressListState.State.SUCCESS
                )
            }
        }
    }

    fun onCreateAddressClicked() {
        mutableAddressListState.update { state ->
            state + UserAddressListState.Event.OpenCreateAddressEvent
        }
    }

    fun consumeEventList(eventList: List<UserAddressListState.Event>) {
        mutableAddressListState.update { state ->
            state - eventList
        }
    }
    //using for ios
    fun onUserAddressChanged(userAddressUuid: String) {
        sharedScope.launch {
            addressInteractor.saveSelectedUserAddress(userAddressUuid)
            mutableAddressListState.update { state ->
                state + UserAddressListState.Event.GoBack
            }
            mutableAddressListState.update { state ->
                state.copy(state = UserAddressListState.State.LOADING)
            }
            println("EVENTS ${addressListState.value.eventList}")
        }
    }
}
