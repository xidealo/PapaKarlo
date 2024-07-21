package com.bunbeauty.shared.presentation.cafe_list

import com.bunbeauty.shared.Constants.WORKING_HOURS_DIVIDER
import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeWithOpenStateListUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.model.cafe.CafeWithOpenState
import com.bunbeauty.shared.presentation.base.SharedViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CafeListViewModel(
    private val cafeInteractor: ICafeInteractor,
    private val observeCafeWithOpenStateListUseCase: ObserveCafeWithOpenStateListUseCase,
    private val observeCartUseCase: ObserveCartUseCase
) : SharedViewModel() {

    private val mutableCafeItemListState = MutableStateFlow(CafeListState())
    val cafeListState = mutableCafeItemListState.asCommonStateFlow()

    private var observeCafeListJob: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableCafeItemListState.update { oldState ->
            oldState.copy(
                state = CafeListState.State.Error(throwable)
            )
        }
    }

    init {
        observeCart()
    }

    private fun observeCart() {
        sharedScope.launch(exceptionHandler) {
            observeCartUseCase().collectLatest { cartTotalAndCount ->
                mutableCafeItemListState.update { state ->
                    state.copy(cartCostAndCount = cartTotalAndCount)
                }
            }
        }
    }

    fun observeCafeList() {
        sharedScope.launch(exceptionHandler) {
            observeCafeListJob?.cancel()
            observeCafeListJob = observeCafeWithOpenStateListUseCase().onEach { cafeWithOpenStateList ->
                mutableCafeItemListState.update { oldState ->
                    oldState.copy(
                        cafeList = cafeWithOpenStateList.map { cafeWithOpenState ->
                            cafeWithOpenState.toCafeItem()
                        },
                        state = CafeListState.State.Success
                    )
                }
            }.launchIn(sharedScope)
        }
    }

    fun onCafeCardClicked(uuid: String) {
        mutableCafeItemListState.update { oldState ->
            oldState + CafeListState.Event.OpenCafeOptionsBottomSheet(
                uuid = uuid
            )
        }
    }

    private fun CafeWithOpenState.toCafeItem(): CafeItem {
        val fromTime = cafeInteractor.getCafeTime(cafe.fromTime)
        val toTime = cafeInteractor.getCafeTime(cafe.toTime)

        return CafeItem(
            uuid = cafe.uuid,
            address = cafe.address,
            phone = cafe.phone,
            workingHours = "$fromTime$WORKING_HOURS_DIVIDER$toTime",
            cafeOpenState = openState
        )
    }

    fun consumeEventList(eventList: List<CafeListState.Event>) {
        mutableCafeItemListState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }
}
