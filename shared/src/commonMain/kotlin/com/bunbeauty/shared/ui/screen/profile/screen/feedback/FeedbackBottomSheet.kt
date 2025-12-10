package com.bunbeauty.shared.ui.screen.profile.screen.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bunbeauty.designsystem.ui.element.card.NavigationIconCard
import com.bunbeauty.shared.OpenExternalSource
import com.bunbeauty.shared.presentation.profile.ProfileState
import com.bunbeauty.designsystem.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.shared.ui.screen.profile.screen.feedback.model.LinkUI
import com.bunbeauty.shared.ui.screen.profile.screen.profile.ProfileViewState
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import papakarlo.shared.generated.resources.Res
import papakarlo.shared.generated.resources.action_feedback_instagram
import papakarlo.shared.generated.resources.action_feedback_play_market
import papakarlo.shared.generated.resources.action_feedback_vk
import papakarlo.shared.generated.resources.ic_google_play
import papakarlo.shared.generated.resources.ic_instagram
import papakarlo.shared.generated.resources.ic_link
import papakarlo.shared.generated.resources.ic_vk
import papakarlo.shared.generated.resources.title_feedback

@Composable
fun FeedBackBottomSheetScreen(
    feedBackBottomSheetUI: ProfileViewState.FeedBackBottomSheetUI,
    onAction: (ProfileState.Action) -> Unit,
) {
    FoodDeliveryModalBottomSheet(
        onDismissRequest = {
            onAction(ProfileState.Action.CloseFeedbackBottomSheet)
        },
        isShown = feedBackBottomSheetUI.isShown,
        title = stringResource(Res.string.title_feedback),
    ) {
        FeedbackScreen(
            linkList = feedBackBottomSheetUI.linkList,
        )
    }
}

@Composable
private fun FeedbackScreen(
    linkList: ImmutableList<LinkUI>,
    openExternalSource: OpenExternalSource = koinInject<OpenExternalSource>(),
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        linkList.forEach { link ->
            NavigationIconCard(
                iconId = link.iconId,
                iconDescriptionStringId = link.labelId,
                labelStringId = link.labelId,
                label = link.value,
                elevated = false,
                onClick = {
                    openExternalSource.openLink(uri = link.value)
                },
            )
        }
    }
}

@Preview
@Composable
private fun FeedbackScreenPreview() {
    FoodDeliveryTheme {
        FeedbackScreen(
            linkList =
                persistentListOf(
                    LinkUI(
                        uuid = "",
                        labelId = Res.string.action_feedback_vk,
                        iconId = Res.drawable.ic_vk,
                        value = "https://vk.com/link",
                    ),
                    LinkUI(
                        uuid = "",
                        labelId = Res.string.action_feedback_instagram,
                        iconId = Res.drawable.ic_instagram,
                        value = "https://instagram.com/link",
                    ),
                    LinkUI(
                        uuid = "",
                        labelId = Res.string.action_feedback_play_market,
                        iconId = Res.drawable.ic_google_play,
                        value = "https://googleplay.com/link",
                    ),
                    LinkUI(
                        uuid = "",
                        labelId = null,
                        iconId = Res.drawable.ic_link,
                        value = "https://unknown.link.com/path",
                    ),
                ),
        )
    }
}
