package com.bunbeauty.papakarlo.feature.cafe.screen.cafelist

import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.cafe_list.CafeListState

class CafeListUiStateMapper(
    private val stringUtil: IStringUtil
) {
    fun map(cafeListState: CafeListState): CafeListUi {
        return CafeListUi(
            topCartUi = TopCartUi(
                cost = cafeListState.cartCostAndCount?.cost?.let { cost ->
                    stringUtil.getCostString(cost)
                } ?: "",
                count = cafeListState.cartCostAndCount?.count ?: ""
            ),
            cafeList = cafeListState.cafeList.map { cafeItem ->
                CafeItemAndroid(
                    uuid = cafeItem.uuid,
                    address = cafeItem.address,
                    phone = cafeItem.phone,
                    workingHours = cafeItem.workingHours,
                    cafeStatusText = stringUtil.getCafeStatusText(cafeItem.cafeOpenState),
                    cafeOpenState = cafeItem.cafeOpenState
                )
            },
            state = cafeListState.state
        )
    }
}
