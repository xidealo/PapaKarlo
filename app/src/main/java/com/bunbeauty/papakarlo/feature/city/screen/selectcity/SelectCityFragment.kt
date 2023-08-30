package com.bunbeauty.papakarlo.feature.city.screen.selectcity

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.city.screen.selectcity.SelectCityFragmentDirections.toMenuFragment
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import com.bunbeauty.shared.domain.model.city.City
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCityFragment : BaseFragment(R.layout.layout_compose) {

    override val viewModel: SelectCityViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCityList()
        viewBinding.root.setContentWithTheme {
            val uiState by viewModel.cityListUiState.collectAsStateWithLifecycle()
            SelectCityScreen(uiState.cityListState)
            LaunchedEffect(uiState.eventList) {
                handleEventList(uiState.eventList)
            }
        }
    }

    @Composable
    private fun SelectCityScreen(cityListState: SelectCityUIState.CityListState) {
        FoodDeliveryScaffold(title = stringResource(R.string.title_select_city)) {
            when (cityListState) {
                SelectCityUIState.CityListState.Loading -> {
                    LoadingScreen()
                }
                is SelectCityUIState.CityListState.Success -> {
                    SelectCitySuccessScreen(cityListState.cityList)
                }
                SelectCityUIState.CityListState.Error -> {
                    ErrorScreen(R.string.error_select_city_loading) {
                        viewModel.getCityList()
                    }
                }
            }
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

    private fun handleEventList(eventList: List<SelectCityEvent>) {
        eventList.forEach { event ->
            when (event) {
                SelectCityEvent.NavigateToMenu -> findNavController().navigateSafe(toMenuFragment())
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SelectCitySuccessScreenPreview() {
        val city = City(
            uuid = "",
            name = "Москва",
            timeZone = ""
        )
        FoodDeliveryTheme {
            SelectCityScreen(
                SelectCityUIState.CityListState.Success(
                    cityList = listOf(city, city, city)
                )
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SelectCityLoadingScreenPreview() {
        FoodDeliveryTheme {
            SelectCityScreen(SelectCityUIState.CityListState.Loading)
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun SelectCityErrorScreenPreview() {
        FoodDeliveryTheme {
            SelectCityScreen(SelectCityUIState.CityListState.Error)
        }
    }
}
