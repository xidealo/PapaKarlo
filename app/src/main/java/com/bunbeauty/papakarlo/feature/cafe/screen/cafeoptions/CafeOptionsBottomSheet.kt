package com.bunbeauty.papakarlo.feature.cafe.screen.cafeoptions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.cafe.model.CafeOptions
import com.bunbeauty.papakarlo.feature.cafe.screen.cafelist.CafeListViewState
import com.bunbeauty.shared.Constants.COORDINATES_DIVIDER
import com.bunbeauty.shared.Constants.MAPS_LINK
import com.bunbeauty.shared.Constants.PHONE_LINK
import com.bunbeauty.shared.presentation.cafe_list.CafeList

@Composable
fun CafeOptionsBottomSheet(
    cafeOptionUI: CafeListViewState.CafeOptionUI,
    onAction: (CafeList.Action) -> Unit
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(CafeList.Action.OnCloseCafeOptionBottomSheetClicked)
        },
        isShown = cafeOptionUI.isShown,
        title = cafeOptionUI.cafeOptions?.title.orEmpty()
    ) {
        if (cafeOptionUI.cafeOptions == null) {
            CircularProgressBar(
                modifier = Modifier
                    .padding(16.dp)
                    .align(CenterHorizontally)
            )
        } else {
            CafeOptionsSuccessScreen(cafeOptionUI.cafeOptions)
        }
    }
}

@Composable
private fun CafeOptionsSuccessScreen(cafeOptions: CafeOptions) {
    val activity = LocalActivity.current
    NavigationIconCard(
        iconId = R.drawable.ic_call,
        iconDescriptionStringId = R.string.description_cafe_options_call,
        label = cafeOptions.callToCafe,
        elevated = false
    ) {
        val uri = (PHONE_LINK + cafeOptions.phone).toUri()
        goByUri(uri = uri, action = Intent.ACTION_DIAL, activity = activity)
    }
    NavigationIconCard(
        modifier = Modifier.padding(top = 8.dp),
        iconId = R.drawable.ic_address,
        iconDescriptionStringId = R.string.description_cafe_options_map,
        label = cafeOptions.showOnMap,
        elevated = false
    ) {
        val uri =
            (MAPS_LINK + cafeOptions.latitude + COORDINATES_DIVIDER + cafeOptions.longitude).toUri()
        goByUri(uri = uri, action = Intent.ACTION_VIEW, activity = activity)
    }
}

private fun goByUri(uri: Uri, action: String, activity: Activity?) {
    val intent = Intent(action, uri)
    activity?.startActivity(intent)
}

@Preview
@Composable
private fun CafeOptionsSuccessScreenPreview() {
    FoodDeliveryTheme {
        CafeOptionsBottomSheet(
            CafeListViewState.CafeOptionUI(
                cafeOptions = CafeOptions(
                    title = "улица Чапаева, д 22А",
                    showOnMap = "На карте: улица Чапаева, д 22А",
                    callToCafe = "Позвонить: +7 (900) 900-90-90",
                    phone = "",
                    latitude = 0.0,
                    longitude = 0.0
                ),
                isShown = true
            ),
            onAction = {}
        )
    }
}
