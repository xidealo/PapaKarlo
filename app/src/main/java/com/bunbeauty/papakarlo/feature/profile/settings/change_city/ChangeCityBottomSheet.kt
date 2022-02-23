package com.bunbeauty.papakarlo.feature.profile.settings.change_city

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.domain.model.City
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.compose.element.Title
import com.bunbeauty.papakarlo.compose.item.CityItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetChangeCityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeCityBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_change_city) {

    override val viewModel: ChangeCityViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetChangeCityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetChangeCityCvMain.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val cityList: List<City> by viewModel.cityList.collectAsState()
                ChangeCityScreen(cityList)
            }
        }
    }

    @Composable
    private fun ChangeCityScreen(cityList: List<City>) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Title(textStringId = R.string.title_change_city)
            cityList.forEachIndexed { index, city ->
                val topSpace = if (index == 0) {
                    FoodDeliveryTheme.dimensions.mediumSpace
                } else {
                    FoodDeliveryTheme.dimensions.smallSpace
                }
                val bottomSpace = if (index == cityList.lastIndex) {
                    FoodDeliveryTheme.dimensions.mediumSpace
                } else {
                    0.dp
                }
                CityItem(
                    modifier = Modifier.padding(
                        start = FoodDeliveryTheme.dimensions.mediumSpace,
                        end = FoodDeliveryTheme.dimensions.mediumSpace,
                        top = topSpace,
                        bottom = bottomSpace,
                    ),
                    cityName = city.name,
                    hasShadow = false
                ) {
                    viewModel.onCitySelected(city)
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