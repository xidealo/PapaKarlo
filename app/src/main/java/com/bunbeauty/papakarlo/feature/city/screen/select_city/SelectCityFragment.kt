package com.bunbeauty.papakarlo.feature.city.screen.select_city

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.shared.domain.model.City
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentSelectCityBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCityFragment : BaseFragment(R.layout.fragment_select_city) {

    override val viewModel: SelectCityViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentSelectCityBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCityList()
        viewBinding.fragmentSelectCityCvMain.compose {
            val cityList by viewModel.cityListState.collectAsState()
            SelectCityScreen(cityList)
        }
    }

    @Composable
    private fun SelectCityScreen(cityListState: State<List<City>>) {
        when (cityListState) {
            is State.Success -> {
                SelectCitySuccessScreen(cityListState.data)
            }
            is State.Loading -> {
                LoadingScreen()
            }
            is State.Error -> {
                ErrorScreen(message = cityListState.message) {
                    viewModel.getCityList()
                }
            }
            else -> Unit
        }
    }

    @Composable
    private fun SelectCitySuccessScreen(cityList: List<City>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            itemsIndexed(cityList) { i, city ->
                CityItem(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                    ),
                    cityName = city.name
                ) {
                    viewModel.onCitySelected(city)
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SelectCitySuccessScreenPreview() {
        val city = City(
            uuid = "",
            name = "Москва",
            timeZone = ""
        )
        SelectCityScreen(
            State.Success(
                listOf(
                    city,
                    city,
                    city,
                )
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SelectCityLoadingScreenPreview() {
        SelectCityScreen(State.Loading())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SelectCityErrorScreenPreview() {
        SelectCityScreen(State.Error("Не удалось загрузить список городов"))
    }
}