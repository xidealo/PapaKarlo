package com.bunbeauty.shared.ui.screen.city.screen.selectcity

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.designsystem.ui.screen.LoadingScreen
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.presentation.selectcity.SelectCityDataState
import com.bunbeauty.shared.presentation.selectcity.SelectCityViewModel
import com.bunbeauty.shared.ui.screen.city.ui.CityItem
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.error_select_city_loading
import papakarlo.shared.generated.resources.title_select_city

@Composable
private fun SelectCityDataState.DataState.mapState(): SelectCityViewState =
    SelectCityViewState(
        state =
            when (state) {
                SelectCityDataState.DataState.State.LOADING -> SelectCityViewState.State.Loading
                SelectCityDataState.DataState.State.SUCCESS -> SelectCityViewState.State.Success
                SelectCityDataState.DataState.State.ERROR -> SelectCityViewState.State.Error
            },
        cityList = cityList,
    )

@Composable
fun SelectCityRoute(
    viewModel: SelectCityViewModel = koinViewModel(),
    goToMenuFragment: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(SelectCityDataState.Action.GetCityList)
    }
    val onAction =
        remember {
            { event: SelectCityDataState.Action ->
                viewModel.onAction(event)
            }
        }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    SelectCityEffect(
        consumeEventList = consumeEffects,
        goToMenuFragment = goToMenuFragment,
        effects = effects,
    )
    SelectCityScreen(
        viewState = viewState.mapState(),
        onAction = onAction,
    )
}

@Composable
private fun SelectCityScreen(
    viewState: SelectCityViewState,
    onAction: (SelectCityDataState.Action) -> Unit,
) {
    FoodDeliveryScaffold(title = stringResource(Res.string.title_select_city)) {
        when (viewState.state) {
            SelectCityViewState.State.Loading -> {
                LoadingScreen()
            }

            is SelectCityViewState.State.Success -> {
                SelectCitySuccessScreen(
                    viewState = viewState,
                    onAction = onAction,
                )
            }

            SelectCityViewState.State.Error -> {
                ErrorScreen(
                    mainTextId = Res.string.error_select_city_loading,
                    onClick = { onAction(SelectCityDataState.Action.OnRefreshClicked) },
                )
            }
        }
    }
}

@Composable
private fun SelectCitySuccessScreen(
    viewState: SelectCityViewState,
    onAction: (SelectCityDataState.Action) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = spacedBy(8.dp),
        contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
    ) {
        items(viewState.cityList) { city ->
            CityItem(
                cityName = city.name,
                onClick = {
                    onAction(SelectCityDataState.Action.OnCitySelected(city))
                },
            )
        }
    }
}

@Composable
private fun SelectCityEffect(
    effects: List<SelectCityDataState.Event>,
    goToMenuFragment: () -> Unit,
    consumeEventList: () -> Unit,
) {
    LaunchedEffect(effects) {
        effects.forEach { event ->
            when (event) {
                SelectCityDataState.Event.NavigateToMenu -> goToMenuFragment()
            }
            consumeEventList()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectCitySuccessScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            viewState =
                SelectCityViewState(
                    state = SelectCityViewState.State.Success,
                    cityList =
                        listOf(
                            City(
                                uuid = "123",
                                name = "Кимры",
                                timeZone = "",
                            ),
                            City(
                                uuid = "123",
                                name = "Москва",
                                timeZone = "",
                            ),
                        ),
                ),
            onAction = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectCityLoadingScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            viewState =
                SelectCityViewState(
                    state = SelectCityViewState.State.Loading,
                    cityList =
                        listOf(
                            City(
                                uuid = "123",
                                name = "Кимры",
                                timeZone = "",
                            ),
                            City(
                                uuid = "123",
                                name = "Москва",
                                timeZone = "",
                            ),
                        ),
                ),
            onAction = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectCityErrorScreenPreview() {
    FoodDeliveryTheme {
        SelectCityScreen(
            viewState =
                SelectCityViewState(
                    state = SelectCityViewState.State.Error,
                    cityList =
                        listOf(
                            City(
                                uuid = "123",
                                name = "Кимры",
                                timeZone = "",
                            ),
                        ),
                ),
            onAction = { },
        )
    }
}
