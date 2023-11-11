package com.bunbeauty.papakarlo.feature.city.screen.changecity

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.delegates.nullableArgument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.city.screen.CityUI
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CityListBottomSheet : ComposeBottomSheet<CityUI>() {

    private var cityList by argument<List<CityUI>>()
    private var selectedCityUuid by nullableArgument<String>()

    @Composable
    override fun Content() {
        CityListScreen(
            cityList = cityList,
            scrolledToTop = ::toggleDraggable,
            onAddressClicked = { city ->
                callback?.onResult(city)
                dismiss()
            },
            selectedCityUuid = selectedCityUuid
        )
    }

    companion object {
        private const val TAG = "CityListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            cityList: List<CityUI>,
            selectedCityUuid: String?
        ) = suspendCoroutine { continuation ->
            CityListBottomSheet().apply {
                this.cityList = cityList
                this.selectedCityUuid = selectedCityUuid
                callback = object : Callback<CityUI> {
                    override fun onResult(result: CityUI?) {
                        continuation.resume(result)
                    }
                }
                show(fragmentManager, TAG)
            }
        }
    }
}

@Composable
private fun CityListScreen(
    cityList: List<CityUI>,
    selectedCityUuid: String?,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (CityUI) -> Unit
) {
    FoodDeliveryLazyBottomSheet(
        titleStringId = R.string.common_city,
        scrolledToTop = scrolledToTop
    ) {
        items(cityList) {city ->
            CityItem(
                cityName = city.name,
                elevated = false,
                isSelected = city.uuid == selectedCityUuid,
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
                ),
                CityUI(
                    uuid = "2",
                    name = "City 2",
                )
            ),
            scrolledToTop = {},
            onAddressClicked = {},
            selectedCityUuid = "1"
        )
    }
}
