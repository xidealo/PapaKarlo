package com.bunbeauty.papakarlo.feature.city.screen.change_city

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.delegates.nullableArgument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.DragHandle
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import com.bunbeauty.shared.domain.model.City
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CityListBottomSheet : ComposeBottomSheet<City>() {

    private var cityList by argument<List<City>>()
    private var selectedCityUuid by nullableArgument<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.root.setContent {
            CityListScreen(
                cityList = cityList,
                scrolledToTop = { isScrolledToTop ->
                    behavior.isDraggable = isScrolledToTop
                },
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
    val listState = rememberLazyListState()
    val itemPosition by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { itemPosition }.collect { itemPosition ->
            scrolledToTop(itemPosition == 0)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 16.dp)
    ) {
        DragHandle()

        Text(

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(R.string.common_city),
            style = FoodDeliveryTheme.typography.titleMedium.bold,
            color = FoodDeliveryTheme.colors.mainColors.onSurface,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f, false),
            state = listState,
            contentPadding = PaddingValues(vertical = FoodDeliveryTheme.dimensions.mediumSpace)
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
}

@Preview(showSystemUi = true)
@Composable
private fun CityListScreenPreview() {
    FoodDeliveryTheme {
        CityListScreen(
            cityList = listOf(
                City(
                    uuid = "1",
                    name = "1",
                    timeZone = "1",
                ),
                City(
                    uuid = "2",
                    name = "2",
                    timeZone = "2",
                ),
            ),
            scrolledToTop = {},
            onAddressClicked = {},
            selectedCityUuid = "1"
        )
    }
}
