package com.bunbeauty.papakarlo.presentation.cafe

import com.bunbeauty.domain.model.local.cafe.Cafe
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.cafe.ICafeUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.base.TopbarCartViewModel
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.presentation.view_model.base.adapter.CafeAdapterModel
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeRepo: CafeRepo,
    cartProductRepo: CartProductRepo,
    private val stringUtil: IStringHelper,
    productHelper: IProductHelper,
    private val cafeUtil: ICafeUtil
) : TopbarCartViewModel(cartProductRepo, stringUtil, productHelper) {

    val cafeListFlow by lazy {
        cafeRepo.cafeEntityListFlow.map { cafeList -> cafeList.map(::toItemModel) }
    }

    fun onCafeCardClick(cafeAdapterModel: CafeAdapterModel) {
        router.navigate(toCafeOptionsBottomSheet(cafeAdapterModel))
    }

    private fun toItemModel(cafe: Cafe): CafeAdapterModel {
        return CafeAdapterModel(
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