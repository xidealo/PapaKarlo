package com.bunbeauty.papakarlo.feature.city.screen.change_city

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bottomSheetShape
import com.bunbeauty.papakarlo.databinding.BottomSheetChangeCityBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import com.bunbeauty.shared.domain.model.City
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeCityBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_change_city) {

    override val viewModel: ChangeCityViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetChangeCityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetChangeCityCvMain.setContentWithTheme {
            val cityList: List<City> by viewModel.cityList.collectAsState()
            ChangeCityScreen(cityList)
        }
    }

    @Composable
    private fun ChangeCityScreen(cityList: List<City>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(bottomSheetShape)
                .background(FoodDeliveryTheme.colors.mainColors.surface)
        ) {
            Title(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.title_change_city
            )
            LazyColumn(
                contentPadding = PaddingValues(
                    start = FoodDeliveryTheme.dimensions.mediumSpace,
                    end = FoodDeliveryTheme.dimensions.mediumSpace,
                    bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                )
            ) {
                itemsIndexed(cityList) { i, city ->
                    CityItem(
                        modifier = Modifier.padding(
                            top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                        ),
                        cityName = city.name,
                        hasShadow = false
                    ) {
                        viewModel.onCitySelected(city)
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ChangeCityScreenPreview() {
        ChangeCityScreen(
            cityList = listOf(
                City(
                    uuid = "",
                    name = "Москва",
                    timeZone = ""
                ),
                City(
                    uuid = "",
                    name = "Дубна",
                    timeZone = ""
                ),
                City(
                    uuid = "",
                    name = "Кимры",
                    timeZone = ""
                ),
            )
        )
    }

    @Preview
    @Composable
    private fun OneListItemChangeCityScreenPreview() {
        ChangeCityScreen(
            cityList = listOf(
                City(
                    uuid = "",
                    name = "Москва",
                    timeZone = ""
                )
            )
        )
    }
}
