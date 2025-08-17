package com.bunbeauty.papakarlo.feature.city.screen.selectcity

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.city.ui.CityItem
import com.bunbeauty.shared.domain.model.city.City
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import org.koin.androidx.compose.koinViewModel

// TODO need refactoring
@Composable
fun SelectCityRoute(
    viewModel: SelectCityViewModel = koinViewModel(),
    goToMenuFragment: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCityList()
    }

    val viewState by viewModel.cityListUiState.collectAsStateWithLifecycle()

    val consumeEffects = remember {
        {
            viewModel.consumeEventList(viewState.eventList)
        }
    }

    SelectCityEffect(
        consumeEventList = consumeEffects,
        goToMenuFragment = goToMenuFragment,
        eventList = viewState.eventList
    )
    SelectCityScreen(
        cityListState = viewState.cityListState,
        onCitySelected = { city ->
            viewModel.onCitySelected(city = city)
        }
    )
}

@Composable
private fun SelectCityScreen(
    cityListState: SelectCityUIState.CityListState,
    onCitySelected: (City) -> Unit
) {
    FoodDeliveryScaffold(title = stringResource(R.string.title_select_city)) {
        when (cityListState) {
            SelectCityUIState.CityListState.Loading -> {
                LoadingScreen()
            }

            is SelectCityUIState.CityListState.Success -> {
                SelectCitySuccessScreen(
                    cityListState.cityList.toPersistentList(),
                    onCitySelected = onCitySelected
                )
            }

            SelectCityUIState.CityListState.Error -> {
                ErrorScreen(R.string.error_select_city_loading) {
                    // viewModel.getCityList()
                }
            }
        }
    }
}

@Composable
private fun SelectCitySuccessScreen(
    cityList: ImmutableList<City>,
    onCitySelected: (City) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = spacedBy(8.dp),
        contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
    ) {
        items(cityList) { city ->
            CityItem(
                cityName = city.name,
                onClick = {
                    onCitySelected(city)
                }
            )
        }
    }
}

@Composable
private fun SelectCityEffect(
    goToMenuFragment: () -> Unit,
    consumeEventList: () -> Unit,
    eventList: List<SelectCityEvent>
) {
    eventList.forEach { event ->
        when (event) {
            SelectCityEvent.NavigateToMenu -> goToMenuFragment()
        }
    }
    consumeEventList()
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
            ),
            onCitySelected = {
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SelectCityLoadingScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            SelectCityUIState.CityListState.Loading,

            onCitySelected = {
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SelectCityErrorScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            SelectCityUIState.CityListState.Error,
            onCitySelected = {
            }
        )
    }
}
