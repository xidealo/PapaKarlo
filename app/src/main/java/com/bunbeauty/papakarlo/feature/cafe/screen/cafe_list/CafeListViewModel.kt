package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.feature.cafe.model.CafeItem
import com.bunbeauty.shared.Constants.WORKING_HOURS_DIVIDER
import com.bunbeauty.shared.domain.feature.city.GetSelectedCityTimeZoneUseCase
import com.bunbeauty.shared.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.shared.domain.model.cafe.Cafe
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CafeListViewModel(
    private val cafeInteractor: ICafeInteractor,
    private val getSelectedCityTimeZoneUseCase: GetSelectedCityTimeZoneUseCase,
) : CartViewModel() {

    private val mutableCafeItemList: MutableStateFlow<State<List<CafeItem>>> =
        MutableStateFlow(State.Loading())
    val cafeItemList: StateFlow<State<List<CafeItem>>> =
        mutableCafeItemList.asStateFlow()

    private var observeMinutesOfDayJob: Job? = null

    fun getCafeItemList() {
        viewModelScope.launch {
            observeMinutesOfDayJob?.cancel()

            mutableCafeItemList.value = cafeInteractor.getCafeList().toState()
            if (mutableCafeItemList.value is State.Success) {
                val timeZone = getSelectedCityTimeZoneUseCase()
                observeMinutesOfDayJob = cafeInteractor.observeCafeList(timeZone).launchOnEach { cafeList ->
                    mutableCafeItemList.value = cafeList.toState()
                }
            }
        }
    }

    fun onCafeCardClicked(cafeItem: CafeItem) {
        router.navigate(CafeListFragmentDirections.toCafeOptionsBottomSheet(cafeItem.uuid))
    }

    private suspend fun List<Cafe>?.toState(): State<List<CafeItem>> {
        return this?.map { cafe ->
            toItemModel(cafe)
        }.toState(resourcesProvider.getString(R.string.error_cafe_list_loading))
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
        val cafeStatus = cafeInteractor.getCafeStatus(cafe, timeZone)

        return CafeItem(
            uuid = cafe.uuid,
            address = cafe.address,
            workingHours = "$fromTime$WORKING_HOURS_DIVIDER$toTime",
            isOpenMessage = isOpenMessage,
            cafeStatus = cafeStatus
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
}
