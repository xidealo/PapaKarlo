package com.bunbeauty.papakarlo.feature.profile.screen.feedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.shared.Constants.INSTAGRAM_LINK
import com.bunbeauty.shared.Constants.PLAY_MARKET_LINK
import com.bunbeauty.shared.Constants.VK_LINK

class FeedbackBottomSheet : ComposeBottomSheet<Any>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContentWithTheme {
            FeedbackScreen(onItemClick = ::goByLink)
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
    onItemClick: (String) -> Unit
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_feedback) {
        NavigationIconCard(
            iconId = R.drawable.ic_vk,
            iconDescription = R.string.description_feedback_vk,
            labelStringId = R.string.action_feedback_vk,
            elevated = false
        ) {
            onItemClick(VK_LINK)
        }
        NavigationIconCard(
            modifier = Modifier.padding(top = 8.dp),
            iconId = R.drawable.ic_instagram,
            iconDescription = R.string.description_feedback_instagram,
            labelStringId = R.string.action_feedback_instagram,
            elevated = false
        ) {
            onItemClick(INSTAGRAM_LINK)
        }
        NavigationIconCard(
            modifier = Modifier.padding(top = 8.dp),
            iconId = R.drawable.ic_gp,
            iconDescription = R.string.description_feedback_play_market,
            labelStringId = R.string.action_feedback_play_market,
            elevated = false
        ) {
            onItemClick(PLAY_MARKET_LINK)
        }
    }
}

@Preview
@Composable
private fun FeedbackScreenPreview() {
    FoodDeliveryTheme {
        FeedbackScreen {}
    }
}
