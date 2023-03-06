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
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.feature.address.ui.AddressItem
import com.bunbeauty.shared.domain.model.City
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CityListBottomSheet : ComposeBottomSheet<City>() {

    private var cityList by argument<List<City>>()

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
                }
            )
        }
    }

    companion object {
        private const val TAG = "CafeAddressListBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
            cityList: List<City>,
        ) = suspendCoroutine { continuation ->
            CityListBottomSheet().apply {
                this.cityList = cityList
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
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
            text = stringResource(R.string.common_city),
            style = FoodDeliveryTheme.typography.titleMedium.bold,
            color = FoodDeliveryTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier.weight(1f, false),
            state = listState,
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            itemsIndexed(cityList) { i, city ->
                AddressItem(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                    ),
                    address = city.name,
                    isClickable = true,
                    hasShadow = false
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
        )
    }
}
