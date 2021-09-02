package com.bunbeauty.papakarlo.presentation.cafe

import androidx.lifecycle.viewModelScope
import com.bunbeauty.domain.model.ui.Cafe
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.cafe.ICafeUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.presentation.util.string.IStringUtil
import com.bunbeauty.presentation.view_model.base.adapter.CafeItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeUtil: ICafeUtil,
    stringUtil: IStringUtil,
    cartProductRepo: CartProductRepo,
    productHelper: IProductHelper
) : CartViewModel(cartProductRepo, stringUtil, productHelper) {

    private val mutableCafeItemList: MutableStateFlow<List<CafeItem>> =
        MutableStateFlow(emptyList())
    val cafeItemList: StateFlow<List<CafeItem>> = mutableCafeItemList.asStateFlow()

    @Inject
    fun subscribeOnCafeList(cafeRepo: CafeRepo) {
        cafeRepo.observeCafeList().onEach { cafeList ->
            mutableCafeItemList.value = cafeList.map(::toItemModel)
        }.launchIn(viewModelScope)
    }

    fun onCafeCardClicked(cafeItem: CafeItem) {
        router.navigate(toCafeOptionsBottomSheet(cafeItem))
    }

    private fun toItemModel(cafe: Cafe): CafeItem {
        return CafeItem(
            uuid = cafe.uuid,
            address = stringUtil.getCafeAddressString(cafe.cafeAddress),
            workingHours = stringUtil.getWorkingHoursString(cafe),
            workingTimeMessage = stringUtil.getIsClosedMessage(cafe),
            workingTimeMessageColor = cafeUtil.getIsClosedColor(cafe),
            phone = cafe.phone,
            latitude = cafe.latitude,
            longitude = cafe.longitude,
        )
    }
}