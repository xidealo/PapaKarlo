package com.bunbeauty.shared.ui.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.ui.topbar.LocalStatusBarColor
import com.bunbeauty.shared.presentation.MainViewModel
import com.bunbeauty.shared.ui.navigation.FoodDeliveryNavHost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.designsystem.generated.resources.Res
import papakarlo.designsystem.generated.resources.error_no_internet
import papakarlo.designsystem.generated.resources.warning_no_order_available

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val mainState by viewModel.mainState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    HandleEventList(
        eventList = mainState.eventList,
        snackbarHostState = snackbarHostState,
        consumeEventList = viewModel::consumeEventList,
    )

    val color = FoodDeliveryTheme.colors.mainColors.surface
    val statusBarColor = remember { mutableStateOf(color) }

    CompositionLocalProvider(
        LocalStatusBarColor provides statusBarColor,
    ) {
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize(),
            snackbarHost = {
                FoodDeliverySnackbarHost(
                    snackbarHostState = snackbarHostState,
                    paddingBottom = mainState.paddingBottomSnackbar,
                )
            },
            containerColor = statusBarColor.value,
        ) { paddingsValues ->
            Column(
                modifier =
                    modifier
                        .fillMaxSize()
                        .padding(paddingValues = paddingsValues),
            ) {
                ConnectionErrorMessage(visible = mainState.connectionLost)
                StatusBarMessage(statusBarMessage = mainState.statusBarMessage)

                Box(
                    modifier =
                        Modifier
                            .fillMaxSize(),
                ) {
                    FoodDeliveryNavHost(
                        showInfoMessage = { message, padding ->
                            viewModel.showInfoMessage(
                                text = message,
                                paddingBottom = padding,
                            )
                        },
                        showErrorMessage = { message ->
                            viewModel.showErrorMessage(
                                message,
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun ConnectionErrorMessage(visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(tween(300)),
        exit = slideOutVertically(tween(300)),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(FoodDeliveryTheme.colors.mainColors.error)
                    .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.error_no_internet),
                style = FoodDeliveryTheme.typography.bodyMedium,
                color = FoodDeliveryTheme.colors.mainColors.onError,
            )
        }
    }
}

@Composable
private fun StatusBarMessage(statusBarMessage: MainState.StatusBarMessage) {
    AnimatedVisibility(
        visible = statusBarMessage.isVisible,
        enter = slideInVertically(tween(300)),
        exit = slideOutVertically(tween(300)),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(FoodDeliveryTheme.colors.statusColors.warning)
                    .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (statusBarMessage.isVisible) {
                Text(
                    text = stringResource(Res.string.warning_no_order_available),
                    style = FoodDeliveryTheme.typography.bodyMedium,
                    color = FoodDeliveryTheme.colors.statusColors.onStatus,
                )
            }
        }
    }
}

@Composable
private fun FoodDeliverySnackbarHost(
    snackbarHostState: SnackbarHostState,
    paddingBottom: Int,
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier =
            Modifier
                .padding(bottom = paddingBottom.dp)
                .imePadding(),
    ) { snackbarData ->
        (snackbarData.visuals as? FoodDeliverySnackbarVisuals)?.let { visuals ->
            val containerColor =
                when (visuals.foodDeliveryMessage.type) {
                    FoodDeliveryMessageType.INFO -> FoodDeliveryTheme.colors.mainColors.primary
                    FoodDeliveryMessageType.ERROR -> FoodDeliveryTheme.colors.mainColors.error
                }
            val contentColor =
                when (visuals.foodDeliveryMessage.type) {
                    FoodDeliveryMessageType.INFO -> FoodDeliveryTheme.colors.mainColors.onPrimary
                    FoodDeliveryMessageType.ERROR -> FoodDeliveryTheme.colors.mainColors.onError
                }
            Snackbar(
                snackbarData = snackbarData,
                containerColor = containerColor,
                contentColor = contentColor,
            )
        }
    }
}

@Composable
private fun HandleEventList(
    eventList: List<MainState.Event>,
    snackbarHostState: SnackbarHostState,
    consumeEventList: (List<MainState.Event>) -> Unit,
) {
    LaunchedEffect(eventList) {
        eventList.forEach { event ->
            when (event) {
                is MainState.Event.ShowMessageEvent -> {
                    val snackbarJob =
                        launch {
                            snackbarHostState.showSnackbar(
                                visuals =
                                    FoodDeliverySnackbarVisuals(
                                        event.message,
                                    ),
                            )
                        }
                    delay(2_000)
                    snackbarJob.cancel()
                }
            }
        }

        consumeEventList(eventList)
    }
}
