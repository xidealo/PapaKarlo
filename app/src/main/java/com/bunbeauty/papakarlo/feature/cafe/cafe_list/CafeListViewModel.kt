package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.CartViewModel
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeListFragmentDirections.*
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.string.IStringUtil
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeInteractor: ICafeInteractor,
    private val resourcesProvider: IResourcesProvider,
    private val stringUtil: IStringUtil
) : CartViewModel() {

    private val mutableCafeItemList: MutableStateFlow<List<CafeItem>> =
        MutableStateFlow(emptyList())
    val cafeItemList: StateFlow<List<CafeItem>> = mutableCafeItemList.asStateFlow()

    init {
        observeCafeList()
    }

    fun observeCafeList() {
        cafeInteractor.observeCafeList().onEach { cafePreviewList ->
            mutableCafeItemList.value = cafePreviewList.map(::toItemModel)
        }.launchIn(viewModelScope)
    }

    fun onCafeCardClicked(cafeItem: CafeItem) {
        router.navigate(toCafeOptionsBottomSheet(cafeItem.uuid))
    }

    private fun toItemModel(cafePreview: CafePreview): CafeItem {
        val isOpenColor = if (cafePreview.isOpen) {
            if (cafePreview.closeIn == null) {
                resourcesProvider.getColorByAttr(R.attr.colorOpen)
            } else {
                resourcesProvider.getColorByAttr(R.attr.colorCloseSoon)
            }
        } else {
            resourcesProvider.getColorByAttr(R.attr.colorClosed)
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

        return CafeItem(
            uuid = cafePreview.uuid,
            address = cafePreview.address,
            workingHours = stringUtil.getWorkingHoursString(cafePreview),
            isOpenMessage = isOpenMessage,
            isOpenColor = isOpenColor
        )
    }

    fun getMinuteString(closeIn: Int): String {
        val minuteStringId = when {
            (closeIn / 10 == 1) -> R.string.msg_cafe_minutes
            (closeIn % 10 == 1) -> R.string.msg_cafe_minute
            (closeIn % 10 in 2..4) -> R.string.msg_cafe_minutes_234
            else -> R.string.msg_cafe_minutes
        }
        return resourcesProvider.getString(minuteStringId)
    }
}