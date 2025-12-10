package com.bunbeauty.shared.ui.screen.cafe.screen.cafelist

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import papakarlo.shared.generated.resources.Res
import com.bunbeauty.shared.ui.screen.cafe.model.CafeOptions
import com.bunbeauty.shared.ui.screen.cafe.ui.CafeItemAndroid
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState
import com.bunbeauty.shared.presentation.cafe_list.CafeList
import kotlinx.collections.immutable.toPersistentList
import papakarlo.shared.generated.resources.action_cafe_options_call
import papakarlo.shared.generated.resources.action_cafe_options_show_map
import papakarlo.shared.generated.resources.msg_cafe_close_soon
import papakarlo.shared.generated.resources.msg_cafe_closed
import papakarlo.shared.generated.resources.msg_cafe_minute
import papakarlo.shared.generated.resources.msg_cafe_minutes
import papakarlo.shared.generated.resources.msg_cafe_minutes_234
import papakarlo.shared.generated.resources.msg_cafe_open

@Composable
fun CafeList.DataState.toViewState(): CafeListViewState {
    val throwable = throwable
    return when {
        throwable != null -> CafeListViewState.Error(throwable = throwable)
        isLoading -> CafeListViewState.Loading
        else ->
            CafeListViewState.Success(
                cafeList =
                    cafeList
                        .map { cafeItem ->
                            CafeItemAndroid(
                                uuid = cafeItem.uuid,
                                address = cafeItem.address,
                                phone = cafeItem.phone,
                                workingHours = cafeItem.workingHours,
                                cafeStatusText = getCafeStatusText(cafeItem.cafeOpenState),
                                cafeOpenState = cafeItem.cafeOpenState,
                                isLast = cafeItem.isLast,
                            )
                        }.toPersistentList(),
                cafeOptionUI =
                    CafeListViewState.CafeOptionUI(
                        isShown = selectedCafe != null,
                        cafeOptions =
                            selectedCafe?.let { cafe ->
                                CafeOptions(
                                    title = cafe.address,
                                    showOnMap = stringResource(Res.string.action_cafe_options_show_map) + cafe.address,
                                    callToCafe = stringResource(Res.string.action_cafe_options_call) + cafe.phone,
                                    phone = cafe.phone,
                                    latitude = cafe.latitude,
                                    longitude = cafe.longitude,
                                )
                            },
                    ),
            )
    }
}

@Composable
private fun getCafeStatusText(cafeOpenState: CafeOpenState): String =
    when (cafeOpenState) {
        is CafeOpenState.Opened -> stringResource(Res.string.msg_cafe_open)
        is CafeOpenState.CloseSoon -> {
            stringResource(Res.string.msg_cafe_close_soon) +
                    cafeOpenState.minutesUntil +
                    getMinuteString(cafeOpenState.minutesUntil)
        }

        is CafeOpenState.Closed -> stringResource(Res.string.msg_cafe_closed)
    }

@Composable
private fun getMinuteString(closeIn: Int): String {
    val minuteStringId =
        when {
            (closeIn / 10 == 1) -> Res.string.msg_cafe_minutes
            (closeIn % 10 == 1) -> Res.string.msg_cafe_minute
            (closeIn % 10 in 2..4) -> Res.string.msg_cafe_minutes_234
            else -> Res.string.msg_cafe_minutes
        }
    return stringResource(minuteStringId)
}
