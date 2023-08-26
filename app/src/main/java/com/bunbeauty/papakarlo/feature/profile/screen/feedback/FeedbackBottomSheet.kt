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
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme

class FeedbackBottomSheet : ComposeBottomSheet<Any>() {

    private var feedbackArgument by argument<FeedbackArgument>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            FeedbackScreen(
                linkList = feedbackArgument.linkList,
                onItemClick = ::goByLink
            )
        }
    }

    private fun goByLink(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "FeedbackBottomSheet"

        fun show(
            fragmentManager: FragmentManager,
            feedbackArgument: FeedbackArgument
        ) = FeedbackBottomSheet().apply {
            this.feedbackArgument = feedbackArgument
            show(fragmentManager, TAG)
        }
    }
}

@Composable
private fun FeedbackScreen(
    linkList: List<LinkUI>,
    onItemClick: (String) -> Unit
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_feedback) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            linkList.forEach { link ->
                NavigationIconCard(
                    iconId = link.iconId,
                    iconDescriptionStringId = link.labelId,
                    labelStringId = link.labelId,
                    label = link.value,
                    elevated = false,
                    onClick = {
                        onItemClick(link.value)
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
            linkList = listOf(
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
            onItemClick = {}
        )
    }
}
