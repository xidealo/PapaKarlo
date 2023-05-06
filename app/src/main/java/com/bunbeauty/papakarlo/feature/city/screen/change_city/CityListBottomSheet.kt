package com.bunbeauty.papakarlo.feature.city.screen.change_city

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.delegates.nullableArgument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryLazyBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import com.bunbeauty.shared.domain.model.city.City
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CityListBottomSheet : ComposeBottomSheet<City>() {

    private var cityList by argument<List<City>>()
    private var selectedCityUuid by nullableArgument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
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
    }

    companion object {
        private const val TAG = "CafeAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            cityList: List<City>,
            selectedCityUuid: String?,
        ) = suspendCoroutine { continuation ->
            CityListBottomSheet().apply {
                this.cityList = cityList
                this.selectedCityUuid = selectedCityUuid
                callback = object : Callback<City> {
                    override fun onResult(result: City?) {
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
    cityList: List<City>,
    selectedCityUuid: String?,
    scrolledToTop: (Boolean) -> Unit,
    onAddressClicked: (City) -> Unit,
) {
    FoodDeliveryLazyBottomSheet(
        titleStringId = R.string.common_city,
        scrolledToTop = scrolledToTop
    ) {
        itemsIndexed(cityList) { i, city ->
            CityItem(
                modifier = Modifier.padding(
                    top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                ),
                cityName = city.name,
                elevated = false,
                isSelected = city.uuid == selectedCityUuid
            ) {
                onAddressClicked(city)
            }
        }
    }
}

@Preview
@Composable
private fun CityListScreenPreview() {
    FoodDeliveryTheme {
        CityListScreen(
            cityList = listOf(
                City(
                    uuid = "1",
                    name = "City 1",
                    timeZone = "1",
                ),
                City(
                    uuid = "2",
                    name = "City 2",
                    timeZone = "2",
                ),
            ),
            scrolledToTop = {},
            onAddressClicked = {},
            selectedCityUuid = "1"
        )
    }
}
