package com.bunbeauty.cafe.presentation.cafe_list

import com.bunbeauty.core.base.BaseAction
import com.bunbeauty.core.base.BaseDataState
import com.bunbeauty.core.base.BaseEvent
import com.bunbeauty.core.model.cafe.Cafe

interface CafeList {
    data class DataState(
        val cafeList: List<CafeItem> = emptyList(),
        val isLoading: Boolean,
        val isShownCafeOptionBottomSheet: Boolean,
        val throwable: Throwable? = null,
        val selectedCafe: Cafe? = null,
    ) : BaseDataState

    sealed interface Action : BaseAction {
        data object Init : Action

        data object BackClicked : Action

        data class OnCafeClicked(
            val cafeUuid: String,
        ) : Action

        data object OnCloseCafeOptionBottomSheetClicked : Action

        data object OnRefreshClicked : Action
    }

    sealed interface Event : BaseEvent {
        data object Back : Event
    }
}
