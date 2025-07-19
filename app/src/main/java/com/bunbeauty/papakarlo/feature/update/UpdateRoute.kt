package com.bunbeauty.papakarlo.feature.update

import android.content.Intent
import androidx.activity.compose.LocalActivity
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.presentation.splash.Splash
import com.bunbeauty.shared.presentation.update.UpdateState
import com.bunbeauty.shared.presentation.update.UpdateViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateRoute(
    viewModel: UpdateViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.onAction(
            UpdateState.Action.Init(
                linkType = LinkType.GOOGLE_PLAY
            )
        )
    }

    val viewState by viewModel.dataState.collectAsStateWithLifecycle()

    val onAction = remember {
        { action: UpdateState.Action ->
            viewModel.onAction(action)
        }
    }

    val effects by viewModel.events.collectAsStateWithLifecycle()
    val consumeEffects = remember {
        {
            viewModel.consumeEvents(effects)
        }
    }

    UpdateEffect(
        effects = effects,
        consumeEffects = consumeEffects
    )
    UpdateScreen(viewState = viewState.mapState(), onAction = onAction)
}

@Composable
fun UpdateState.DataState.mapState(): UpdateViewState {
    return UpdateViewState(
        state = when (state) {
            UpdateState.DataState.State.LOADING -> UpdateViewState.State.Loading
            UpdateState.DataState.State.SUCCESS -> UpdateViewState.State.Success(link = link)
            UpdateState.DataState.State.ERROR -> UpdateViewState.State.Error
        }
    )
}

@Composable
fun UpdateEffect(
    effects: List<UpdateState.Event>,
    consumeEffects: () -> Unit,
) {
    val localActivity = LocalActivity.current
    LaunchedEffect(effects) {
        effects.forEach { effect ->
            when (effect) {
                is UpdateState.Event.NavigateToUpdateEvent -> {
                    val uri = effect.linkValue.toUri()
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    localActivity?.startActivity(intent)
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
        title = stringResource(R.string.title_update_new_app_version)
    ) {
        when (viewState.state) {
            UpdateViewState.State.Loading -> LoadingScreen()
            UpdateViewState.State.Error -> ErrorScreen(
                mainTextId = R.string.error_common_data_loading,
                onClick = {
                    onAction(
                        UpdateState.Action.Init(
                            linkType = LinkType.GOOGLE_PLAY
                        )
                    )
                }
            )

            is UpdateViewState.State.Success -> UpdateScreenSuccess(
                viewState = viewState.state,
                onAction = onAction
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(FoodDeliveryTheme.colors.statusColors.warning),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(64.dp),
                painter = painterResource(R.drawable.ic_google_play),
                tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                contentDescription = stringResource(R.string.description_google_play)
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            text = stringResource(id = R.string.msg_update_new_title_app_version),
            style = FoodDeliveryTheme.typography.titleMedium.bold,
            color = FoodDeliveryTheme.colors.mainColors.onBackground,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = stringResource(id = R.string.msg_update_new_app_version),
            style = FoodDeliveryTheme.typography.bodyLarge,
            color = FoodDeliveryTheme.colors.mainColors.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            textStringId = R.string.action_update_update
        ) {
            viewState.link?.linkValue?.let { link ->
                onAction(UpdateState.Action.UpdateClick(linkValue = link))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateScreenSuccessPreview() {
    FoodDeliveryTheme {
        UpdateScreen(
            viewState = UpdateViewState(
                state = UpdateViewState.State.Success(
                    Link(
                        uuid = "1",
                        type = LinkType.GOOGLE_PLAY,
                        linkValue = "https://play.google.com/store/apps/details?id=1"
                    )
                )
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateScreenErrorPreview() {
    FoodDeliveryTheme {
        UpdateScreen(
            viewState = UpdateViewState(
                state = UpdateViewState.State.Error
            ),
            onAction = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UpdateScreenLoadingPreview() {
    FoodDeliveryTheme {
        UpdateScreen(
            viewState = UpdateViewState(
                state = UpdateViewState.State.Loading
            ),
            onAction = {}
        )
    }
}
