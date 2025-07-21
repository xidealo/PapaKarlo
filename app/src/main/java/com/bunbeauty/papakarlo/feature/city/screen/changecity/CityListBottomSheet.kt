package com.bunbeauty.papakarlo.feature.city.screen.changecity

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.city.screen.CityUI
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import com.bunbeauty.papakarlo.feature.profile.screen.settings.SettingsViewState
import com.bunbeauty.shared.presentation.settings.SettingsState

@Composable
fun CityListBottomSheetScreen(
    cityListBottomSheetUI: SettingsViewState.CityListBottomSheetUI,
    onAction: (SettingsState.Action) -> Unit
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(SettingsState.Action.CloseLogoutBottomSheet)
        },
        isShown = cityListBottomSheetUI.isShown,
        title = stringResource(R.string.common_city)
    ) {
        CityListScreen(
            cityList = cityListBottomSheetUI.cityListUI,
            onAddressClicked = { city ->
                onAction(SettingsState.Action.OnCitySelected(cityUuid = city.uuid))
            }
        )
    }
}

@Composable
private fun CityListScreen(
    cityList: List<CityUI>,
    onAddressClicked: (CityUI) -> Unit
) {
    LazyColumn {
        items(cityList) { city ->
            CityItem(
                cityName = city.name,
                elevated = false,
                isSelected = city.isSelected,
                onClick = {
                    onAddressClicked(city)
                }
            )
        }
    }
}

@Preview
@Composable
private fun CityListScreenPreview() {
    FoodDeliveryTheme {
        CityListScreen(
            cityList = listOf(
                CityUI(
                    uuid = "1",
                    name = "City 1",
                    isSelected = true
                ),
                CityUI(
                    uuid = "2",
                    name = "City 2",
                    isSelected = false
                )
            ),
            onAddressClicked = {}
        )
    }
}
