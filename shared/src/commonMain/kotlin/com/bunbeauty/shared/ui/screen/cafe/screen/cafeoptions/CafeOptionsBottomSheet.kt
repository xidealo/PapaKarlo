package com.bunbeauty.shared.ui.screen.cafe.screen.cafeoptions

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.element.CircularProgressBar
import com.bunbeauty.designsystem.ui.element.card.NavigationIconCard
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.shared.Constants.COORDINATES_DIVIDER
import com.bunbeauty.shared.Constants.MAPS_LINK
import com.bunbeauty.shared.Constants.PHONE_LINK
import com.bunbeauty.shared.OpenExternalSource
import com.bunbeauty.shared.presentation.cafe_list.CafeList
import com.bunbeauty.shared.ui.screen.cafe.model.CafeOptions
import com.bunbeauty.shared.ui.screen.cafe.screen.cafelist.CafeListViewState
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.description_cafe_options_call
import papakarlo.shared.generated.resources.description_cafe_options_map
import papakarlo.shared.generated.resources.ic_address
import papakarlo.shared.generated.resources.ic_call

@Composable
fun CafeOptionsBottomSheet(
    cafeOptionUI: CafeListViewState.CafeOptionUI,
    onAction: (CafeList.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CafeList.Action.OnCloseCafeOptionBottomSheetClicked)
        },
        isShown = cafeOptionUI.isShown,
        title = cafeOptionUI.cafeOptions?.title.orEmpty(),
    ) {
        if (cafeOptionUI.cafeOptions == null) {
            CircularProgressBar(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .align(CenterHorizontally),
            )
        } else {
            CafeOptionsSuccessScreen(cafeOptionUI.cafeOptions)
        }
    }
}

@Composable
private fun CafeOptionsSuccessScreen(
    cafeOptions: CafeOptions,
    openExternalSource: OpenExternalSource = koinInject<OpenExternalSource>(),
) {
    NavigationIconCard(
        iconId = Res.drawable.ic_call,
        iconDescriptionStringId = Res.string.description_cafe_options_call,
        label = cafeOptions.callToCafe,
        elevated = false,
    ) {
        val uri = (PHONE_LINK + cafeOptions.phone)
        openExternalSource.openPhone(uri)
    }
    NavigationIconCard(
        modifier = Modifier.padding(top = 8.dp),
        iconId = Res.drawable.ic_address,
        iconDescriptionStringId = Res.string.description_cafe_options_map,
        label = cafeOptions.showOnMap,
        elevated = false,
    ) {
        val uri =
            (MAPS_LINK + cafeOptions.latitude + COORDINATES_DIVIDER + cafeOptions.longitude)
        openExternalSource.openMap(uri)
    }
}

@Preview
@Composable
private fun CafeOptionsSuccessScreenPreview() {
    FoodDeliveryTheme {
        CafeOptionsBottomSheet(
            CafeListViewState.CafeOptionUI(
                cafeOptions =
                    CafeOptions(
                        title = "улица Чапаева, д 22А",
                        showOnMap = "На карте: улица Чапаева, д 22А",
                        callToCafe = "Позвонить: +7 (900) 900-90-90",
                        phone = "",
                        latitude = 0.0,
                        longitude = 0.0,
                    ),
                isShown = true,
            ),
            onAction = {},
        )
    }
}
