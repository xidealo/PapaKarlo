package com.bunbeauty.papakarlo.feature.cafe.screen.cafelist

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState
import com.bunbeauty.shared.presentation.cafe_list.CafeList
import kotlinx.collections.immutable.toPersistentList

private const val EMPTY_COST = ""
private const val EMPTY_COUNT = ""

@Composable
fun CafeList.DataState.toViewState(): CafeListViewState {
    val throwable = throwable
    return when {
        throwable != null -> CafeListViewState.Error(throwable = throwable)
        isLoading -> CafeListViewState.Loading
        else -> CafeListViewState.Success(
            topCartUi = TopCartUi(
                cost = cartCostAndCount?.cost ?: EMPTY_COST,
                count = cartCostAndCount?.count ?: EMPTY_COUNT
            ),
            cafeList = cafeList.map { cafeItem ->
                CafeItemAndroid(
                    uuid = cafeItem.uuid,
                    address = cafeItem.address,
                    phone = cafeItem.phone,
                    workingHours = cafeItem.workingHours,
                    cafeStatusText = getCafeStatusText(cafeItem.cafeOpenState),
                    cafeOpenState = cafeItem.cafeOpenState,
                    isLast = cafeItem.isLast
                )
            }.toPersistentList()
        )
    }
}

@Composable
private fun getCafeStatusText(cafeOpenState: CafeOpenState): String {
    return when (cafeOpenState) {
        is CafeOpenState.Opened -> stringResource(R.string.msg_cafe_open)
        is CafeOpenState.CloseSoon -> {
            stringResource(R.string.msg_cafe_close_soon) +
                cafeOpenState.minutesUntil +
                getMinuteString(cafeOpenState.minutesUntil)
        }

        is CafeOpenState.Closed -> stringResource(R.string.msg_cafe_closed)
    }
}

@Composable
private fun getMinuteString(closeIn: Int): String {
    val minuteStringId = when {
        (closeIn / 10 == 1) -> R.string.msg_cafe_minutes
        (closeIn % 10 == 1) -> R.string.msg_cafe_minute
        (closeIn % 10 in 2..4) -> R.string.msg_cafe_minutes_234
        else -> R.string.msg_cafe_minutes
    }
    return stringResource(minuteStringId)
}
