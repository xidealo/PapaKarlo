package com.bunbeauty.papakarlo.feature.profile.screen.feedback

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryModalBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.profile.screen.feedback.model.LinkUI
import com.bunbeauty.papakarlo.feature.profile.screen.profile.ProfileViewState
import com.bunbeauty.shared.presentation.profile.ProfileState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


private fun goByLink(link: String, context: Context?) {
    val uri = link.toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context?.startActivity(intent)
}

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
        title = stringResource(R.string.title_feedback)
    ) {
        FeedbackScreen(
            linkList = feedBackBottomSheetUI.linkList,
        )
    }
}

@Composable
private fun FeedbackScreen(
    linkList: ImmutableList<LinkUI>,
) {
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        linkList.forEach { link ->
            NavigationIconCard(
                iconId = link.iconId,
                iconDescriptionStringId = link.labelId,
                labelStringId = link.labelId,
                label = link.value,
                elevated = false,
                onClick = {
                    goByLink(link = link.value, context = context)
                }
            )
        }
    }
}

@Preview
@Composable
private fun FeedbackScreenPreview() {
    FoodDeliveryTheme {
        FeedbackScreen(
            linkList = persistentListOf(
                LinkUI(
                    uuid = "",
                    labelId = R.string.action_feedback_vk,
                    iconId = R.drawable.ic_vk,
                    value = "https://vk.com/link"
                ),
                LinkUI(
                    uuid = "",
                    labelId = R.string.action_feedback_instagram,
                    iconId = R.drawable.ic_instagram,
                    value = "https://instagram.com/link"
                ),
                LinkUI(
                    uuid = "",
                    labelId = R.string.action_feedback_play_market,
                    iconId = R.drawable.ic_google_play,
                    value = "https://googleplay.com/link"
                ),
                LinkUI(
                    uuid = "",
                    labelId = null,
                    iconId = R.drawable.ic_link,
                    value = "https://unknown.link.com/path"
                )
            ),
        )
    }
}
