package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.feature.cafe.model.CafeItem
import com.bunbeauty.shared.Constants.WORKING_HOURS_DIVIDER
import com.bunbeauty.shared.domain.feature.cart.ObserveCartUseCase
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.interactor.cafe.GetCafeListUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.model.cafe.Cafe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CafeListViewModel(
    private val cafeInteractor: ICafeInteractor,
    private val getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase,
    private val getCafeListUseCase: GetCafeListUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
) : CartViewModel() {

    private val mutableCafeItemListState = MutableStateFlow(CafeListState())
    val cafeListState = mutableCafeItemListState.asStateFlow()

    private var observeMinutesOfDayJob: Job? = null

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
        viewModelScope.launch(exceptionHandler) {
            observeCartUseCase().collectLatest { cartTotalAndCount ->
                mutableCafeItemListState.update { state ->
                    state.copy(cartCostAndCount = cartTotalAndCount)
                }
            }
        }
    }

    fun getCafeItemList() {
        viewModelScope.launch(exceptionHandler) {
            observeMinutesOfDayJob?.cancel()

            mutableCafeItemListState.update { oldState ->
                oldState.copy(
                    cafeList = getCafeListUseCase().toItemModels(),
                    state = CafeListState.State.Success
                )
            }
            if (mutableCafeItemListState.value.cafeList.isNotEmpty()) {
                val timeZone = getSelectedCityTimeZoneUseCase()
                observeMinutesOfDayJob =
                    cafeInteractor.observeCafeList(timeZone).launchOnEach { cafeList ->
                        mutableCafeItemListState.update { oldState ->
                            oldState.copy(
                                cafeList = cafeList.toItemModels()
                            )
                        }
                    }
            }
        }
    }

    fun onCafeCardClicked(cafeItem: CafeItem) {
        mutableCafeItemListState.update { oldState ->
            oldState + CafeListState.Event.OpenCafeOptionsBottomSheet(
                uuid = cafeItem.uuid
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
        val isOpenMessage = if (cafeInteractor.isClosed(cafe, timeZone)) {
            resourcesProvider.getString(R.string.msg_cafe_closed)
        } else {
            cafeInteractor.getCloseIn(cafe, timeZone)?.let { closeIn ->
                resourcesProvider.getString(R.string.msg_cafe_close_soon) +
                    closeIn +
                    getMinuteString(closeIn)
            } ?: resourcesProvider.getString(R.string.msg_cafe_open)
        }

        return CafeItem(
            uuid = cafe.uuid,
            address = cafe.address,
            workingHours = "$fromTime$WORKING_HOURS_DIVIDER$toTime",
            isOpenMessage = isOpenMessage,
            cafeStatus = cafeInteractor.getCafeStatus(cafe, timeZone)
        )
    }

    private fun getMinuteString(closeIn: Int): String {
        val minuteStringId = when {
            (closeIn / 10 == 1) -> R.string.msg_cafe_minutes
            (closeIn % 10 == 1) -> R.string.msg_cafe_minute
            (closeIn % 10 in 2..4) -> R.string.msg_cafe_minutes_234
            else -> R.string.msg_cafe_minutes
        }
        return resourcesProvider.getString(minuteStringId)
    }

    fun consumeEventList(eventList: List<CafeListState.Event>) {
        mutableCafeItemListState.update { state ->
            state.copy(eventList = state.eventList - eventList.toSet())
        }
    }
}
