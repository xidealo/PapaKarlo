package com.bunbeauty.papakarlo.presentation.cafe

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.interactor.cafe.ICafeInteractor
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.fragment.cafe.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.presentation.item.CafeItem
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.presentation.util.string.IStringUtil
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
            if (cafePreview.willCloseIn == null) {
                resourcesProvider.getColorByAttr(R.attr.colorOpen)
            } else {
                resourcesProvider.getColorByAttr(R.attr.colorCloseSoon)
            }
        } else {
            resourcesProvider.getColorByAttr(R.attr.colorClosed)
        }
        val isOpenMessage = if (cafePreview.isOpen) {
            if (cafePreview.willCloseIn == null) {
                resourcesProvider.getString(R.string.msg_cafe_open)
            } else {
                resourcesProvider.getString(R.string.msg_cafe_close_soon) +
                        cafePreview.willCloseIn +
                        resourcesProvider.getString(R.string.msg_cafe_minutes)
            }
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
}