package com.bunbeauty.papakarlo.presentation.cafe

import com.bunbeauty.domain.model.local.cafe.Cafe
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.cafe.ICafeUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringUtil
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.presentation.view_model.base.adapter.CafeItem
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeRepo: CafeRepo,
    private val cafeUtil: ICafeUtil,
    cartProductRepo: CartProductRepo,
    stringUtil: IStringUtil,
    productHelper: IProductHelper
) : CartViewModel(cartProductRepo, stringUtil, productHelper) {

    val cafeListFlow by lazy {
        cafeRepo.cafeEntityListFlow.map { cafeList -> cafeList.map(::toItemModel) }
    }

    fun onCafeCardClick(cafeItem: CafeItem) {
        router.navigate(toCafeOptionsBottomSheet(cafeItem))
    }

    private fun toItemModel(cafe: Cafe): CafeItem {
        return CafeItem(
            uuid = cafe.cafeEntity.id,
            address = stringUtil.toString(cafe.address),
            workingHours = stringUtil.toStringWorkingHours(cafe.cafeEntity),
            workingTimeMessage = cafeUtil.getIsClosedMessage(
                cafe.cafeEntity.fromTime,
                cafe.cafeEntity.toTime,
                DateTime.now()
            ),
            workingTimeMessageColor = cafeUtil.getIsClosedColor(
                cafe.cafeEntity.fromTime,
                cafe.cafeEntity.toTime,
                DateTime.now()
            ),
            phone = cafe.cafeEntity.phone,
            coordinate = cafe.cafeEntity.coordinate
        )
    }
}