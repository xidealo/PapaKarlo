package com.bunbeauty.profile.ui.screen.city.screen.changecity

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.designsystem.model.CityUI
import com.bunbeauty.profile.presentation.settings.SettingsState
import com.bunbeauty.profile.ui.screen.city.ui.CityItem
import com.bunbeauty.profile.ui.screen.settings.SettingsViewState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.common_city

@Composable
fun CityListBottomSheetScreen(
    cityListBottomSheetUI: SettingsViewState.CityListBottomSheetUI,
    onAction: (SettingsState.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(SettingsState.Action.CloseCityListBottomSheet)
        },
        isShown = cityListBottomSheetUI.isShown,
        title = stringResource(Res.string.common_city),
    ) {
        CityListScreen(
            cityList = cityListBottomSheetUI.cityListUI,
            onAddressClicked = { city ->
                onAction(SettingsState.Action.OnCitySelected(cityUuid = city.uuid))
            },
        )
    }
}

@Composable
private fun CityListScreen(
    cityList: List<CityUI>,
    onAddressClicked: (CityUI) -> Unit,
) {
    LazyColumn {
        items(cityList) { city ->
            CityItem(
                cityName = city.name,
                elevated = false,
                isSelected = city.isSelected,
                onClick = {
                    onAddressClicked(city)
                },
            )
        }
    }
}

@Preview
@Composable
private fun CityListScreenPreview() {
    FoodDeliveryTheme {
        CityListScreen(
            cityList =
                listOf(
                    CityUI(
                        uuid = "1",
                        name = "City 1",
                        isSelected = true,
                    ),
                    CityUI(
                        uuid = "2",
                        name = "City 2",
                        isSelected = false,
                    ),
                ),
            onAddressClicked = {},
        )
    }
}
