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
import com.bunbeauty.shared.presentation.selectcity.SelectCityDataState
import com.bunbeauty.shared.presentation.selectcity.SelectCityViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectCityDataState.DataState.mapState(): SelectCityViewState {
    return SelectCityViewState(
        state = when (state) {
            SelectCityDataState.DataState.State.LOADING -> SelectCityViewState.State.Loading
            SelectCityDataState.DataState.State.SUCCESS -> SelectCityViewState.State.Success
            SelectCityDataState.DataState.State.ERROR -> SelectCityViewState.State.Error
        },
        cityList = cityList
    )
}

// TODO need refactoring
@Composable
fun SelectCityRoute(
    viewModel: SelectCityViewModel = koinViewModel(),
    goToMenuFragment: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCityList()
    }
    val onAction = remember {
        { event: SelectCityDataState.Action ->
            viewModel.onAction(event)
        }
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects = remember {
        {
            viewModel.consumeEvents(effects)
        }
    }

    SelectCityEffect(
        consumeEventList = consumeEffects,
        goToMenuFragment = goToMenuFragment,
        effects = effects
    )
    SelectCityScreen(
        viewState = viewState.mapState(),
        onAction = onAction
    )
}

@Composable
private fun SelectCityScreen(
    viewState: SelectCityViewState,
    onAction: (SelectCityDataState.Action) -> Unit
) {
    FoodDeliveryScaffold(title = stringResource(R.string.title_select_city)) {
        when (viewState.state) {
            SelectCityViewState.State.Loading -> {
                LoadingScreen()
            }

            is SelectCityViewState.State.Success -> {
                SelectCitySuccessScreen(
                    viewState = viewState,
                    onAction = onAction
                )
            }

            SelectCityViewState.State.Error -> {
                ErrorScreen(
                    mainTextId = R.string.error_select_city_loading,
                    onClick = { onAction(SelectCityDataState.Action.OnRefreshClicked) }
                )
            }
        }
    }
}

@Composable
private fun SelectCitySuccessScreen(
    viewState: SelectCityViewState,
    onAction: (SelectCityDataState.Action) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = spacedBy(8.dp),
        contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
    ) {
        items(viewState.cityList) { city ->
            CityItem(
                cityName = city.name,
                onClick = {
                    onAction(SelectCityDataState.Action.OnCitySelected(city))
                }
            )
        }
    }
}

@Composable
private fun SelectCityEffect(
    effects: List<SelectCityDataState.Event>,
    goToMenuFragment: () -> Unit,
    consumeEventList: () -> Unit
) {
    LaunchedEffect(effects) {
        effects.forEach { event ->
            when (event) {
                SelectCityDataState.Event.NavigateToMenu -> goToMenuFragment
            }
            consumeEventList()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SelectCitySuccessScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            viewState = SelectCityViewState(
                state = SelectCityViewState.State.Success,
                cityList = listOf(
                    City(
                        uuid = "123",
                        name = "Кимры",
                        timeZone = ""
                    ),
                    City(
                        uuid = "123",
                        name = "Москва",
                        timeZone = ""
                    )
                )
            ),
            onAction = { }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SelectCityLoadingScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            viewState = SelectCityViewState(
                state = SelectCityViewState.State.Loading,
                cityList = listOf(
                    City(
                        uuid = "123",
                        name = "Кимры",
                        timeZone = ""
                    ),
                    City(
                        uuid = "123",
                        name = "Москва",
                        timeZone = ""
                    )
                )
            ),
            onAction = { }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SelectCityErrorScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            viewState = SelectCityViewState(
                state = SelectCityViewState.State.Error,
                cityList = listOf(
                    City(
                        uuid = "123",
                        name = "Кимры",
                        timeZone = ""
                    )
                )
            ),
            onAction = { }
        )
    }
}
