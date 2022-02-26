package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeStatus.*
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.*

class CafeListViewModel(
    private val cafeInteractor: ICafeInteractor,
    private val resourcesProvider: IResourcesProvider,
    private val stringUtil: IStringUtil
) : CartViewModel() {

    private val mutableCafeItemList: MutableStateFlow<List<CafeItemModel>?> = MutableStateFlow(null)
    val cafeItemList: StateFlow<List<CafeItemModel>?> = mutableCafeItemList.asStateFlow()

    init {
        observeCafeList()
    }

    fun onCafeCardClicked(cafeItem: CafeItemModel) {
        router.navigate(toCafeOptionsBottomSheet(cafeItem.uuid))
    }

    private fun observeCafeList() {
        cafeInteractor.observeCafeList().onEach { cafePreviewList ->
            mutableCafeItemList.value = cafePreviewList.map(::toItemModel)
        }.launchIn(viewModelScope)
    }

    private fun toItemModel(cafePreview: CafePreview): CafeItemModel {
        val cafeStatus = if (cafePreview.isOpen) {
            if (cafePreview.closeIn == null) {
                OPEN
            } else {
                CLOSE_SOON
            }
        } else {
            CLOSED
        }
        val isOpenMessage = if (cafePreview.isOpen) {
            cafePreview.closeIn?.let { closeIn ->
                resourcesProvider.getString(R.string.msg_cafe_close_soon) +
                        closeIn +
                        getMinuteString(closeIn)
            } ?: resourcesProvider.getString(R.string.msg_cafe_open)
        } else {
            resourcesProvider.getString(R.string.msg_cafe_closed)
        }

        return CafeItemModel(
            uuid = cafePreview.uuid,
            address = cafePreview.address,
            workingHours = stringUtil.getWorkingHoursString(cafePreview),
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