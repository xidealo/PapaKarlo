package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.state.StateWithError
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.extensions.toStateSuccessOrError
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import core_common.Constants.WORKING_HOURS_DIVIDER
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CafeListViewModel(
    private val cafeInteractor: ICafeInteractor,
    private val resourcesProvider: IResourcesProvider
) : CartViewModel() {

    private val mutableCafeItemList: MutableStateFlow<StateWithError<List<CafeItemModel>>> =
        MutableStateFlow(StateWithError.Loading())
    val cafeItemList: StateFlow<StateWithError<List<CafeItemModel>>> =
        mutableCafeItemList.asStateFlow()

    private var observeMinutesOfDayJob: Job? = null

    fun getCafeItemList() {
        viewModelScope.launch {
            observeMinutesOfDayJob?.cancel()

            mutableCafeItemList.value = cafeInteractor.getCafeList().toState()
            if (mutableCafeItemList.value is StateWithError.Success) {
                observeMinutesOfDayJob = cafeInteractor.observeCafeList().launchOnEach { cafeList ->
                    mutableCafeItemList.value = cafeList.toState()
                }
            }
        }
    }

    fun onCafeCardClicked(cafeItem: CafeItemModel) {
        router.navigate(toCafeOptionsBottomSheet(cafeItem.uuid))
    }

    private suspend fun List<Cafe>?.toState(): StateWithError<List<CafeItemModel>> {
        return this?.map { cafe ->
            toItemModel(cafe)
        }.toStateSuccessOrError(resourcesProvider.getString(R.string.error_cafe_list_loading))
    }

    private suspend fun toItemModel(cafe: Cafe): CafeItemModel {
        val fromTime = cafeInteractor.getCafeTime(cafe.fromTime)
        val toTime = cafeInteractor.getCafeTime(cafe.toTime)
        val isOpenMessage = if (cafeInteractor.isClosed(cafe)) {
            resourcesProvider.getString(R.string.msg_cafe_closed)
        } else {
            cafeInteractor.getCloseIn(cafe)?.let { closeIn ->
                resourcesProvider.getString(R.string.msg_cafe_close_soon) +
                        closeIn +
                        getMinuteString(closeIn)
            } ?: resourcesProvider.getString(R.string.msg_cafe_open)
        }
        val cafeStatus = cafeInteractor.getCafeStatus(cafe)

        return CafeItemModel(
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