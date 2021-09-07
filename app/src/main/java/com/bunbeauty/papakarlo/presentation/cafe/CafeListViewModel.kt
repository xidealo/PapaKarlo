package com.bunbeauty.papakarlo.presentation.cafe

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.util.cafe.ICafeUtil
import com.bunbeauty.papakarlo.di.annotation.Firebase
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.item.CafeItem
import com.bunbeauty.presentation.util.resources.ResourcesProvider
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeUtil: ICafeUtil,
    private val stringUtil: IStringUtil,
    private val resourcesProvider: ResourcesProvider
) : CartViewModel() {

    private var cafeList: List<Cafe> = emptyList()
    private val mutableCafeItemList: MutableStateFlow<List<CafeItem>> =
        MutableStateFlow(emptyList())
    val cafeItemList: StateFlow<List<CafeItem>> = mutableCafeItemList.asStateFlow()

    @Inject
    fun subscribeOnCafeList(@Firebase cafeRepo: CafeRepo) {
        cafeRepo.observeCafeList().onEach { observedCafeList ->
            cafeList = observedCafeList
            mutableCafeItemList.value = observedCafeList.map(::toItemModel)
        }.launchIn(viewModelScope)
    }

    fun onCafeCardClicked(cafeItem: CafeItem) {
        cafeList.find { cafe ->
            cafe.uuid == cafeItem.uuid
        }?.let { cafe ->
            router.navigate(toCafeOptionsBottomSheet(cafe))
        }
    }

    private fun toItemModel(cafe: Cafe): CafeItem {
        return CafeItem(
            uuid = cafe.uuid,
            address = stringUtil.getCafeAddressString(cafe.cafeAddress),
            workingHours = stringUtil.getWorkingHoursString(cafe),
            workingTimeMessage = stringUtil.getIsClosedMessage(cafe),
            workingTimeMessageColor =  resourcesProvider.getColor(cafeUtil.getIsClosedColorId(cafe))
        )
    }
}