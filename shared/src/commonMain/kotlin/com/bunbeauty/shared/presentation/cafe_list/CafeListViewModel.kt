package com.bunbeauty.shared.presentation.cafe_list

import com.bunbeauty.core.Constants.WORKING_HOURS_DIVIDER
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.core.extension.launchSafe
import com.bunbeauty.core.model.cafe.CafeWithOpenState
import com.bunbeauty.core.base.SharedStateViewModel
import com.bunbeauty.core.domain.cafe.ObserveCafeWithOpenStateListUseCase
import com.bunbeauty.shared.presentation.cafe_list.CafeList.Action.OnCloseCafeOptionBottomSheetClicked
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class CafeListViewModel(
    private val cafeInteractor: ICafeInteractor,
    private val observeCafeWithOpenStateListUseCase: ObserveCafeWithOpenStateListUseCase,
) : SharedStateViewModel<CafeList.DataState, CafeList.Action, CafeList.Event>(
        initDataState =
            CafeList.DataState(
                cafeList = listOf(),
                isLoading = true,
                isShownCafeOptionBottomSheet = false,
            ),
    ) {
    private var observeCafeListJob: Job? = null

    override fun reduce(
        action: CafeList.Action,
        dataState: CafeList.DataState,
    ) {
        when (action) {
            CafeList.Action.Init -> observeCafeList()

            is CafeList.Action.OnCafeClicked -> {
                sharedScope.launchSafe(
                    block = {
                        setState {
                            copy(
                                isShownCafeOptionBottomSheet = true,
                                selectedCafe =
                                    cafeInteractor.getCafeByUuid(
                                        cafeUuid = action.cafeUuid,
                                    ),
                            )
                        }
                    },
                    onError = { error ->
                        setState {
                            copy(
                                throwable = error,
                            )
                        }
                    },
                )
            }

            OnCloseCafeOptionBottomSheetClicked -> closeBottomSheet()

            CafeList.Action.OnRefreshClicked -> observeCafeList()

            CafeList.Action.BackClicked -> backClick()
        }
    }

    private fun observeCafeList() {
        observeCafeListJob?.cancel()
        observeCafeListJob =
            sharedScope.launchSafe(
                block = {
                    observeCafeWithOpenStateListUseCase()
                        .collectLatest { cafeWithOpenStateList ->
                            setState {
                                copy(
                                    cafeList =
                                        cafeWithOpenStateList.mapIndexed { index, cafeWithOpenState ->
                                            cafeWithOpenState.toCafeItem(
                                                isLast = index == cafeWithOpenStateList.lastIndex,
                                            )
                                        },
                                    isLoading = false,
                                    throwable = null,
                                )
                            }
                        }
                },
                onError = { error ->
                    setState {
                        copy(
                            throwable = error,
                        )
                    }
                },
            )
    }

    private fun closeBottomSheet() {
        setState {
            copy(
                isShownCafeOptionBottomSheet = false,
                selectedCafe = null,
            )
        }
    }

    private fun backClick() {
        addEvent {
            CafeList.Event.Back
        }
    }

    private fun CafeWithOpenState.toCafeItem(isLast: Boolean): CafeItem {
        val fromTime = cafeInteractor.getCafeTime(cafe.fromTime)
        val toTime = cafeInteractor.getCafeTime(cafe.toTime)

        return CafeItem(
            uuid = cafe.uuid,
            address = cafe.address,
            phone = cafe.phone,
            workingHours = "$fromTime$WORKING_HOURS_DIVIDER$toTime",
            cafeOpenState = openState,
            isLast = isLast,
        )
    }
}
