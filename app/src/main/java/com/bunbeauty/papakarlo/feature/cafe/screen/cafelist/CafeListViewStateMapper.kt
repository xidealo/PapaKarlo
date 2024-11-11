package com.bunbeauty.papakarlo.feature.cafe.screen.cafelist

import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.shared.presentation.cafe_list.CafeList

private const val EMPTY_COST = ""
private const val EMPTY_COUNT = ""

class CafeListViewStateMapper(
    private val stringUtil: IStringUtil
) {
    fun map(cafeListState: CafeList.DataState): CafeListViewState {
        val throwable = cafeListState.throwable
        return CafeListViewState(
            topCartUi = TopCartUi(
                cost = cafeListState.cartCostAndCount?.cost?.let { cost ->
                    stringUtil.getCostString(cost)
                } ?: EMPTY_COST,
                count = cafeListState.cartCostAndCount?.count ?: EMPTY_COUNT
            ),
            cafeList = cafeListState.cafeList.map { cafeItem ->
                CafeItemAndroid(
                    uuid = cafeItem.uuid,
                    address = cafeItem.address,
                    phone = cafeItem.phone,
                    workingHours = cafeItem.workingHours,
                    cafeStatusText = stringUtil.getCafeStatusText(cafeItem.cafeOpenState),
                    cafeOpenState = cafeItem.cafeOpenState,
                    isLast = cafeItem.isLast
                )
            },
            state = when {
                throwable != null -> CafeListViewState.State.Error(throwable)
                cafeListState.isLoading -> CafeListViewState.State.Loading
                else -> CafeListViewState.State.Success
            }
        )
    }
}
