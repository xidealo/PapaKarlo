package com.bunbeauty.shared.ui.screen.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.designsystem.theme.bold
import com.bunbeauty.designsystem.ui.element.FoodDeliveryScaffold
import com.bunbeauty.designsystem.ui.element.button.MainButton
import com.bunbeauty.designsystem.ui.screen.ErrorScreen
import com.bunbeauty.designsystem.ui.screen.LoadingScreen
import com.bunbeauty.shared.OpenExternalSource
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.presentation.update.UpdateState
import com.bunbeauty.shared.presentation.update.UpdateViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.action_update_update
import papakarlo.shared.generated.resources.description_google_play
import papakarlo.shared.generated.resources.error_common_data_loading
import papakarlo.shared.generated.resources.ic_google_play
import papakarlo.shared.generated.resources.msg_update_new_app_version
import papakarlo.shared.generated.resources.msg_update_new_title_app_version
import papakarlo.shared.generated.resources.title_update_new_app_version

@Composable
fun UpdateRoute(viewModel: UpdateViewModel = koinViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.onAction(
            UpdateState.Action.Init(
                linkType = LinkType.GOOGLE_PLAY,
            ),
        )
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction =
        remember {
            { action: UpdateState.Action ->
                viewModel.onAction(action)
            }
        }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects =
        remember {
            {
                viewModel.consumeEvents(effects)
            }
        }

    UpdateEffect(
        effects = effects,
        consumeEffects = consumeEffects,
    )
    UpdateScreen(viewState = viewState.mapState(), onAction = onAction)
}

@Composable
fun UpdateState.DataState.mapState(): UpdateViewState =
    UpdateViewState(
        state =
            when (state) {
                UpdateState.DataState.State.LOADING -> UpdateViewState.State.Loading
                UpdateState.DataState.State.SUCCESS -> UpdateViewState.State.Success(link = link)
                UpdateState.DataState.State.ERROR -> UpdateViewState.State.Error
            },
    )

@Composable
fun UpdateEffect(
    effects: List<UpdateState.Event>,
    consumeEffects: () -> Unit,
    openExternalSource: OpenExternalSource = koinInject<OpenExternalSource>(),
) {
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                is UpdateState.Event.NavigateToUpdateEvent -> {
                    openExternalSource.openLink(uri = effect.linkValue)
                }
            }
        }
        consumeEffects()
    }
}

@Composable
private fun UpdateScreen(
    viewState: UpdateViewState,
    onAction: (UpdateState.Action) -> Unit,
) {
    FoodDeliveryScaffold(
        title = stringResource(Res.string.title_update_new_app_version),
    ) {
        when (viewState.state) {
            UpdateViewState.State.Loading -> LoadingScreen()
            UpdateViewState.State.Error ->
                ErrorScreen(
                    mainTextId = Res.string.error_common_data_loading,
                    onClick = {
                        onAction(
                            UpdateState.Action.Init(
                                linkType = LinkType.GOOGLE_PLAY,
                            ),
                        )
                    },
                )

            is UpdateViewState.State.Success ->
                UpdateScreenSuccess(
                    viewState = viewState.state,
                    onAction = onAction,
                )
        }
    }
}

@Composable
private fun UpdateScreenSuccess(
    viewState: UpdateViewState.State.Success,
    onAction: (UpdateState.Action) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = FoodDeliveryTheme.colors.mainColors.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(weight = 1f))

        Box(
            modifier =
                Modifier
                    .size(size = 120.dp)
                    .clip(CircleShape)
                    .background(color = FoodDeliveryTheme.colors.statusColors.warning),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier =
                    Modifier
                        .padding(start = 8.dp)
                        .size(size = 64.dp),
                painter = painterResource(resource = Res.drawable.ic_google_play),
                tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                contentDescription = stringResource(resource = Res.string.description_google_play),
            )
        }

        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .padding(horizontal = 16.dp),
            text = stringResource(resource = Res.string.msg_update_new_title_app_version),
            style = FoodDeliveryTheme.typography.titleMedium.bold,
            color = FoodDeliveryTheme.colors.mainColors.onBackground,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
            text = stringResource(resource = Res.string.msg_update_new_app_version),
            style = FoodDeliveryTheme.typography.bodyLarge,
            color = FoodDeliveryTheme.colors.mainColors.onBackground,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp),
            textStringId = Res.string.action_update_update,
        ) {
            viewState.link?.linkValue?.let { link ->
                onAction(UpdateState.Action.UpdateClick(linkValue = link))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UpdateScreenSuccessPreview() {
    FoodDeliveryTheme {
        UpdateScreen(
            viewState =
                UpdateViewState(
                    state =
                        UpdateViewState.State.Success(
                            Link(
                                uuid = "1",
                                type = LinkType.GOOGLE_PLAY,
                                linkValue = "https://play.google.com/store/apps/details?id=1",
                            ),
                        ),
                ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UpdateScreenErrorPreview() {
    FoodDeliveryTheme {
        UpdateScreen(
            viewState =
                UpdateViewState(
                    state = UpdateViewState.State.Error,
                ),
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UpdateScreenLoadingPreview() {
    FoodDeliveryTheme {
        UpdateScreen(
            viewState =
                UpdateViewState(
                    state = UpdateViewState.State.Loading,
                ),
            onAction = {},
        )
    }
}
