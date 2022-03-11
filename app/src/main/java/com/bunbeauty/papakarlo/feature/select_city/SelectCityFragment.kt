package com.bunbeauty.papakarlo.feature.select_city

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.domain.model.City
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.item.CityItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentSelectCityBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCityFragment : BaseFragment(R.layout.fragment_select_city) {

    override val viewModel: SelectCityViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentSelectCityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentSelectCityCvMain.compose {
            val cityList by viewModel.cityList.collectAsState()
            SelectCityScreen(cityList)
        }
    }

    @Composable
    private fun SelectCityScreen(cityList: List<City>?) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
        ) {
            if (cityList == null) {
                CircularProgressBar(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
                ) {
                    itemsIndexed(cityList) { i, city ->
                        CityItem(
                            modifier = Modifier.padding(
                                top = FoodDeliveryTheme.dimensions.getTopItemSpaceByIndex(i)
                            ),
                            cityName = city.name
                        ) {
                            viewModel.onCitySelected(city)
                        }
                    }
                }
            }
        }
    }

    private val city = City(
        uuid = "",
        name = "Москва",
        timeZone = ""
    )

    @Preview
    @Composable
    private fun SelectCitySuccessScreenPreview() {
        SelectCityScreen(
            cityList = listOf(
                city,
                city,
                city,
            )
        )
    }

    @Preview
    @Composable
    private fun SelectCityLoadingScreenPreview() {
        SelectCityScreen(null)
    }
}