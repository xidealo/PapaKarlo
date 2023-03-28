package com.bunbeauty.papakarlo.feature.profile.screen.feedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottom_sheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme

class FeedbackBottomSheet : ComposeBottomSheet<Any>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            FeedbackScreen(
                vkLink = resources.getString(R.string.vk_link),
                instagramLink = resources.getString(R.string.instagram_link),
                googlePlayLink = resources.getString(R.string.google_play_link),
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
    vkLink: String,
    instagramLink: String,
    googlePlayLink: String,
    onItemClick: (String) -> Unit,
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_feedback) {
        if (vkLink.isNotEmpty()) {
            NavigationIconCard(
                iconId = R.drawable.ic_vk,
                iconDescription = R.string.description_feedback_vk,
                labelStringId = R.string.action_feedback_vk,
                elevated = false,
                onClick = {
                    onItemClick(vkLink)
                }
            )
            if (instagramLink.isNotEmpty() || googlePlayLink.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if (instagramLink.isNotEmpty()) {
            NavigationIconCard(
                modifier = Modifier.padding(top = 8.dp),
                iconId = R.drawable.ic_instagram,
                iconDescription = R.string.description_feedback_instagram,
                labelStringId = R.string.action_feedback_instagram,
                elevated = false,
                onClick = {
                    onItemClick(instagramLink)
                }
            )
            if (googlePlayLink.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        if (googlePlayLink.isNotEmpty()) {
            NavigationIconCard(
                modifier = Modifier.padding(top = 8.dp),
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

@Preview
@Composable
private fun FeedbackScreenPreview() {
    FoodDeliveryTheme {
        FeedbackScreen(
            vkLink = "vkLink",
            instagramLink = "instagramLink",
            googlePlayLink = "googlePlayLink",
            onItemClick = {},
        )
    }
}
