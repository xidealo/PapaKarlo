package com.bunbeauty.shared.presentation.cafe_list

import com.bunbeauty.shared.Constants.WORKING_HOURS_DIVIDER
import com.bunbeauty.shared.domain.asCommonStateFlow
import com.bunbeauty.shared.domain.feature.cafe.ObserveCafeListUseCase
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.model.cafe.Cafe
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
    private val observeCafeListUseCase: ObserveCafeListUseCase,
    private val getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
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
            observeCafeListJob = observeCafeListUseCase().onEach { cafeList ->
                mutableCafeItemListState.update { oldState ->
                    oldState.copy(
                        cafeList = cafeList.toItemModels(),
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

    private suspend fun List<Cafe>.toItemModels(): List<CafeItem> {
        return this.map { cafe ->
            toItemModel(cafe)
        }
    }

    private suspend fun toItemModel(cafe: Cafe): CafeItem {
        val fromTime = cafeInteractor.getCafeTime(cafe.fromTime)
        val toTime = cafeInteractor.getCafeTime(cafe.toTime)
        val timeZone = getSelectedCityTimeZoneUseCase()

        return CafeItem(
            uuid = cafe.uuid,
            address = cafe.address,
            phone = cafe.phone,
            workingHours = "$fromTime$WORKING_HOURS_DIVIDER$toTime",
            cafeOpenState = cafeInteractor.getCafeStatus(cafe, timeZone),
        )
    }

    fun consumeEventList(eventList: List<CafeListState.Event>) {
        mutableCafeItemListState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }
}
