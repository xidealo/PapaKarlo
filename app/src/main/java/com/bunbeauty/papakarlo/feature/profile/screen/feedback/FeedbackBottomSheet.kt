package com.bunbeauty.papakarlo.feature.profile.screen.feedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.shared.data.socialNetworkLinks
import com.bunbeauty.shared.domain.model.SocialNetworkLinks

class FeedbackBottomSheet : ComposeBottomSheet<Any>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            FeedbackScreen(
                socialNetworkLinks = socialNetworkLinks,
                onItemClick = ::goByLink,
            )
        }
    }

    private fun goByLink(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}

@Composable
private fun FeedbackScreen(
    socialNetworkLinks: SocialNetworkLinks,
    onItemClick: (String) -> Unit,
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_feedback) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            socialNetworkLinks.vkLink?.let { vkLink ->
                NavigationIconCard(
                    iconId = R.drawable.ic_vk,
                    iconDescription = R.string.description_feedback_vk,
                    labelStringId = R.string.action_feedback_vk,
                    elevated = false,
                    onClick = {
                        onItemClick(vkLink)
                    }
                )
            }
            socialNetworkLinks.instagramLink?.let { instagramLink ->
                NavigationIconCard(
                    iconId = R.drawable.ic_instagram,
                    iconDescription = R.string.description_feedback_instagram,
                    labelStringId = R.string.action_feedback_instagram,
                    elevated = false,
                    onClick = {
                        onItemClick(instagramLink)
                    }
                )
            }
            socialNetworkLinks.googlePlayLink?.let { googlePlayLink ->
                NavigationIconCard(
                    iconId = R.drawable.ic_google_play,
                    iconDescription = R.string.description_feedback_play_market,
                    labelStringId = R.string.action_feedback_play_market,
                    elevated = false,
                    onClick = {
                        onItemClick(googlePlayLink)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun FeedbackScreenPreview() {
    FoodDeliveryTheme {
        FeedbackScreen(
            socialNetworkLinks = SocialNetworkLinks(
                vkLink = "vkLink",
                instagramLink = "instagramLink",
                googlePlayLink = "googlePlayLink",
                appStoreLink = null,
            ),
            onItemClick = {},
        )
    }
}
