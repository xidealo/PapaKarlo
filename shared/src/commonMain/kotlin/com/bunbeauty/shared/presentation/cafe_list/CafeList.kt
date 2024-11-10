package com.bunbeauty.shared.presentation.cafe_list

import com.bunbeauty.shared.domain.model.cart.CartCostAndCount
import com.bunbeauty.shared.presentation.base.BaseAction
import com.bunbeauty.shared.presentation.base.BaseDataState
import com.bunbeauty.shared.presentation.base.BaseEvent

interface CafeList {

    data class DataState(
        val cafeList: List<CafeItem> = emptyList(),
        val cartCostAndCount: CartCostAndCount? = null,
        val isLoading: Boolean,
        val throwable: Throwable? = null,
    ) : BaseDataState


    sealed interface Action : BaseAction {
        data object Init : Action
        data class onCafeClicked(val cafeUuid: String) : Action
        data object onRefreshClicked : Action
        data object onCartClicked : Action
    }

    sealed interface Event : BaseEvent {
        data class OpenCafeOptionsBottomSheet(val uuid: String) : Event
        data object OpenConsumerCartProduct : Event
    }
}
